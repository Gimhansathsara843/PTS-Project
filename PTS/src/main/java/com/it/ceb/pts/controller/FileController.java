package com.it.ceb.pts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.it.ceb.pts.domain.DistributionLicense;
import com.it.ceb.pts.domain.FileUploadHeader;
import com.it.ceb.pts.domain.FileUploadModel;
import com.it.ceb.pts.domain.Province;

import com.it.ceb.pts.repo.DistributionLicenseDao;
import com.it.ceb.pts.repo.FileUploadHeaderDao;
import com.it.ceb.pts.repo.MeterProcessDao;
import com.it.ceb.pts.repo.ProvinceDao;


import com.it.ceb.util.common.PathMMS;
import com.it.ceb.util.common.ZipExtractor;
import com.it.ceb.util.common.model.ModelService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileUploadHeaderDao fileUploadHeaderDao;

    @Autowired
    private DistributionLicenseDao distributionLicenseDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private ModelService modelService;

    @Autowired
    private MeterProcessDao meterProcessDao;

    @Transactional
    @PostMapping("/UploadingMeterReadingFileS")
    public ModelAndView uploadingMeterReadingFileS(
            HttpServletRequest request,
            @RequestParam("files") MultipartFile[] files,
            @ModelAttribute("model") FileUploadModel model) throws Exception {

        ModelAndView mv = new ModelAndView("pts/lisenceeBilling/fileUpload", "model", model);

        String division = model.getDivision();
        String billCycle = model.getBillCycle();
        String province = model.getProvince();

        String zipSavePath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province;
        String extractionPath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division;

        boolean filesProcessed = false;
        StringBuilder processingResults = new StringBuilder();
        boolean hasValidZipFiles = false;

        for (MultipartFile file : files) {
            if (!file.isEmpty() && file.getOriginalFilename() != null &&
                    file.getOriginalFilename().toLowerCase().endsWith(".zip")) {
                hasValidZipFiles = true;
                break;
            }
        }

        File zipDir = null;
        File extractDir = null;

        if (hasValidZipFiles) {
            zipDir = new File(zipSavePath);
            extractDir = new File(extractionPath);

            if (!zipDir.exists() && !zipDir.mkdirs()) {
                model.setErrorMessage("Failed to create ZIP directory: " + zipSavePath);
                return mv;
            }

            if (!extractDir.exists() && !extractDir.mkdirs()) {
                model.setErrorMessage("Failed to create extraction directory: " + extractionPath);
                return mv;
            }
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".zip")) {
                    processingResults.append("Skipping invalid file: ").append(originalFilename).append("\n");
                    continue;
                }

                String uniqueFilename = originalFilename;

                if (isFileAlreadyUploaded(billCycle, division, province, originalFilename)) {
                    model.setFileNameConflict(true);
                    model.setOriginalFileName(originalFilename);
                    uniqueFilename = generateUniqueFileName(originalFilename, billCycle, division, province);
                    processingResults.append("Duplicate found. File renamed to: ").append(uniqueFilename).append("\n");
                }

                File zipFile = new File(zipDir, uniqueFilename);
                file.transferTo(zipFile);
                ZipExtractor.unzip(zipFile.getAbsolutePath(), extractionPath);

                try {
                    FileUploadHeader header = new FileUploadHeader();
                    header.setBillCycleNo(Long.parseLong(billCycle));
                    header.setLicenseCode(division);
                    header.setProvinceCode(province);
                    header.setFileName(uniqueFilename);
                    header.setIsUploaded(1L);
                    header.setUploadedBy(getUserName(request));
                    header.setUploadedDate(LocalDate.now());
                    header.setFileType("ZIP");

                    fileUploadHeaderDao.save(header);

                    processingResults.append("Saved metadata for: ").append(originalFilename).append("\n");
                    filesProcessed = true;

                } catch (Exception e) {
                    processingResults.append("Error saving metadata for: ").append(originalFilename)
                            .append(" - ").append(e.getMessage()).append("\n");
                }
            }
        }

        if (filesProcessed) {
            model.setSuccessMessage("Files processed successfully.\n" + processingResults);
        } else {
            model.setErrorMessage("No files were processed.\n" + processingResults);
        }

        // Load dropdown lists
        List<DistributionLicense> licenseList = distributionLicenseDao.getLicenseList();
        List<Province> provinceList = provinceDao.getAllProvince();
        model.setLicenseList(licenseList);
        model.setProvinceList(provinceList);
        mv.addObject("provinceList", new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));

        return mv;
    }

    private String generateUniqueFileName(String baseName, String billCycle, String division, String province) {
        String nameWithoutExt = baseName;
        String extension = "";

        int dotIndex = baseName.lastIndexOf('.');
        if (dotIndex > 0) {
            nameWithoutExt = baseName.substring(0, dotIndex);
            extension = baseName.substring(dotIndex);
        }

        String candidate = baseName;
        int counter = 1;
        while (isFileAlreadyUploaded(billCycle, division, province, candidate)) {
            candidate = nameWithoutExt + counter + extension;
            counter++;
        }

        return candidate;
    }

    private boolean isFileAlreadyUploaded(String billCycle, String division, String province, String fileName) {
        try {
            FileUploadHeader existing = fileUploadHeaderDao.findByCompositeKeyAndFileName(
                    Long.parseLong(billCycle), division, province, fileName);
            return existing != null;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserName(HttpServletRequest request) {
        try {
            if (request.getUserPrincipal() != null) {
                String username = request.getUserPrincipal().getName();
                return username.length() > 15 ? username.substring(0, 15) : username;
            }
        } catch (Exception e) {
            // log if needed
        }
        return "system";
    }

    @Transactional
    @GetMapping(value = "/getBillCycle", produces = "text/plain")
    public @ResponseBody String getBillCycle() throws Exception {
        Long billCycle = meterProcessDao.getCurrentBillCycleNo();
        return billCycle.toString();
    }
}
