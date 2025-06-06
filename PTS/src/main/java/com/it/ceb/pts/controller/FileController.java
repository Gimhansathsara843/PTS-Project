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
import jakarta.annotation.Resource;
import jakarta.enterprise.inject.Model;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        String extractionPath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province + "_zip";

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

                // Delete existing extracted folder
                if (extractDir.exists()) {
                    deleteDirectory(extractDir);
                }
                ZipExtractor.unzip(zipFile.getAbsolutePath(), extractionPath);


                // Move contents of the extracted folder to the parent folder
                File extractedFolder = new File(extractionPath, province);
                if (extractedFolder.exists() && extractedFolder.isDirectory()) {
                    File[] extractedFiles = extractedFolder.listFiles();
                    if (extractedFiles != null) {
                        for (File extractedFile : extractedFiles) {
                            Files.move(
                                    extractedFile.toPath(),
                                    new File(extractionPath, extractedFile.getName()).toPath(),
                                    StandardCopyOption.REPLACE_EXISTING
                            );
                        }
                    }
                    // Delete the now-empty extracted folder
                    deleteDirectory(extractedFolder);
                }

                try {
                    FileUploadHeader header = new FileUploadHeader();
                    header.setBillCycleNo(Long.parseLong(billCycle));
                    header.setLicenseCode(division);
                    header.setProvinceCode(province);
                    header.setFileName(uniqueFilename);
                    header.setIsUploaded(1L);
                    header.setUploadedBy(getUserName(request));
                    header.setUploadedDate(LocalDate.now());
                  //  header.setFileType("ZIP");

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

    // Utility method to delete a directory and its contents
    private boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        return directory.delete();
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
        System.out.println("=================getBillCycle=====================");
        Long billCycle = meterProcessDao.getCurrentBillCycleNo();
        return billCycle.toString();
    }

    @GetMapping("/checkExistingUploads")
    @ResponseBody
    public int checkExistingUploads(@RequestParam("billCycle") String billCycle,
                                    @RequestParam("division") String division,
                                    @RequestParam("province") String province) {
        return fileUploadHeaderDao.countByBillCycleAndLicenseAndProvince(
                Long.parseLong(billCycle), division, province);
    }

//    @GetMapping("/viewUploadDetails")
//    @ResponseBody
//    public List<FileUploadHeader> viewUploads(@RequestParam String billCycle,
//                                              @RequestParam String division,
//                                              @ModelAttribute FileUploadModel model)  {
//        System.out.println("==============enter to the method ======================");
//        List<FileUploadHeader> uploadDetails = List.of(); // Initialize with an empty list
//        try {
//            // Fetch data using the DAO method
//            uploadDetails = fileUploadHeaderDao.getUploadDetails(billCycle, division);
//
//
//            // Log or process the retrieved data
//            for (FileUploadHeader header : uploadDetails) {
//                System.out.println("File Name: " + header.getFileName());
//                System.out.println("Uploaded By: " + header.getUploadedBy());
//                System.out.println("Uploaded Date: " + header.getUploadedDate());
//                System.out.println("Province Code: " + header.getProvinceCode());
//                System.out.println("File Type: " + header.getFileType());
//                System.out.println("-----------------------------");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Failed to fetch data: " + e.getMessage());
//        }
//        System.out.println("==============exit from the method ======================");
//        return uploadDetails;
//    }


    @Transactional
    @GetMapping("/viewUploadDetails")
    public ModelAndView viewUploads(@RequestParam("billCycle") String billCycle,
                                    @RequestParam("division") String division) {
        System.out.println("==============enter to the method ======================");
        ModelAndView modelAndView = new ModelAndView("pts/lisenceeBilling/viewFile");

        try {
            List<FileUploadHeader> uploadDetails = fileUploadHeaderDao.getUploadDetails(billCycle, division);

            if (!uploadDetails.isEmpty()) {
                // Optional: Log details
                for (FileUploadHeader upload : uploadDetails) {

                    System.out.println("File Name: " + upload.getFileName());
                    System.out.println("Uploaded By: " + upload.getUploadedBy());
                    System.out.println("Uploaded Date: " + upload.getUploadedDate());
                    System.out.println("Province Code: " + upload.getProvinceCode());
                //    System.out.println("uploadDetails: " + uploadDetails);

                    modelAndView.addObject("uploadDetails", uploadDetails);
                    modelAndView.addObject("fileType", upload.getFileName());
                    modelAndView.addObject("uploadedBy", upload.getUploadedBy());
                    modelAndView.addObject("uploadedDate", upload.getUploadedDate());
                    modelAndView.addObject("provinceCode", upload.getProvinceCode());
                }
           }

//            modelAndView.addObject("fileName", billCycle);
          //    modelAndView.addObject("uploadDetails", uploadDetails);


            System.out.println("============uploadDetails size: ================ " + uploadDetails);

        } catch (Exception e) {
            System.err.println("Error occurred while fetching upload details: " + e.getMessage());
            e.printStackTrace();
            return new ModelAndView("errorPage", "errorMessage", "Failed to fetch upload details.");
        }

        System.out.println("==============exit from the method ======================");
        return modelAndView;
    }

    @GetMapping("/downloadZipFile")
    public void downloadZipFile(
            @RequestParam("fileName") String fileName,
            @RequestParam("billCycle") String billCycle,
            @RequestParam("division") String division,
            @RequestParam("province") String province,
            HttpServletResponse response) throws IOException {

        // Construct the file path
        String filePath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province + File.separator + fileName;
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("File not found: " + fileName);
            return;
        }

        // Set response headers for file download
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLengthLong(file.length());

        // Write the file to the response output stream
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }


}

