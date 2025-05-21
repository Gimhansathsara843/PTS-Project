package com.it.ceb.pts.controller;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.ceb.pts.domain.*;
import com.it.ceb.util.common.PathMMS;
import com.it.ceb.util.common.Util;
import com.it.ceb.util.common.ZipExtractor;
import com.it.ceb.util.common.model.*;
import com.it.ceb.pts.repo.*;
import com.it.ceb.util.common.ExcelMeterReader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PTSController {

	private String basePath ="C:\\Users\\Dhammika Mahendra\\Documents\\CEB Projects\\PowerTrading_resourcefile\\CEB_PTS_res\\";

	@Autowired
	private DistributionLicenseDao DistributionLicenseDao;
	@Autowired
	private ExcelMeterReader excelMeterReader;
	@Autowired
	private MeterProcessDao meterProcessDao;
	@Autowired
	private MeterReadingDao meterReadingDao;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ProvinceDao provinceDao;



	//login page =============================================================================
	@RequestMapping(value = "WelcomePTS", method = RequestMethod.GET)
	public ModelAndView WelcomePTS(@ModelAttribute("model")  CbrsModel cbrsModel,BindingResult bindingResult, HttpServletRequest request,HttpServletResponse response) throws Exception {

		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				throw new NullPointerException("Session is null");
			}
		} catch (IllegalStateException ex) {
			throw new NullPointerException("Session is Invalid");
		}
		return new ModelAndView("pts/login", "model", cbrsModel);

	}

	//Main Home page ===========================================================================
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView admindashboardpts(@ModelAttribute("cbrsModel") CbrsModel cbrsModel, HttpServletRequest request) {
		return new ModelAndView("pts/home", "model", cbrsModel);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView admindashboardpts2(@ModelAttribute("cbrsModel") CbrsModel cbrsModel, HttpServletRequest request) {
		return new ModelAndView("pts/home", "model", cbrsModel);
	}

	//-------------------------------------------------------------------------------------------
	//						licensee Billing
	//-------------------------------------------------------------------------------------------


	//licenseeeBillingHome
	@Transactional
	@RequestMapping(value = "/licenseeBillingHome", method = RequestMethod.GET)
	public String licenseeBillingHome(Model model) throws Exception{
		return "pts/lisenceeBilling/licenseeBillingHome"; // Return the same page after form submission
	}

	//processMeterReading
	@Transactional
	@RequestMapping(value = "/processMeterReading", method = RequestMethod.GET)
	public String processMeterReading(Model model) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.addAttribute("licenseList",licenseList);
		model.addAttribute("provinceList",new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));
		return "pts/lisenceeBilling/processMeterReading"; // Return the same page after form submission
	}

	//viewMeterReading
	@Transactional
	@RequestMapping(value = "/viewMeterReading", method = RequestMethod.GET)
	public String viewMeterReading(Model model) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.addAttribute("licenseList",licenseList);
		model.addAttribute("provinceList",new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));
		return "pts/lisenceeBilling/viewMeterReading"; // Return the same page after form submission
	}

	//file uploading
	@Transactional
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public ModelAndView uploadFile(@ModelAttribute("model") CbrsModel cbrsModel) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		cbrsModel.setdivisionList(licenseList);
		cbrsModel.setProvinceList(provinceList);
		ModelAndView mv= new ModelAndView("pts/lisenceeBilling/fileUpload", "model", cbrsModel);
		mv.addObject("provinceList", new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));
		return mv;
	}

//	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
//	public ModelAndView uploadFile(@ModelAttribute("model") CbrsModel cbrsModel,@ModelAttribute("FileUploadModel") FileUploadModel fileUploadModel,HttpServletRequest request) throws Exception {
//		ModelAndView mo = new ModelAndView();
//		CbrsModel model=new CbrsModel();
//		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
//		List<Province> provinceList = provinceDao.getAllProvince();
//		fileUploadModel.setProvinceList(provinceList);
//		fileUploadModel.setLicenseList(licenseList);
//		return new ModelAndView("pts/lisenceeBilling/fileUpload", "model", model);
//	}

	//view upload meter points
	@Transactional
	@RequestMapping(value = "/viewFile", method = RequestMethod.GET)
	public String viewMeterPoints(Model model) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.addAttribute("licenseList",licenseList);
		model.addAttribute("provinceList",new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));
		return "pts/lisenceeBilling/viewFile";
	}

	//meter search
	@Transactional
	@RequestMapping(value = "/meterSearch", method = RequestMethod.GET)
	public String viewMeterSearch(Model model) throws Exception{
		return "pts/lisenceeBilling/meterSearch";
	}

	//invoice
	@Transactional
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public String viewInvoice(Model model) throws Exception{
		return "pts/lisenceeBilling/invoice";
	}



	//view config
	@Transactional
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String viewConfig(Model model) throws Exception{
		return "pts/lisenceeBilling/config";
	}

	//-------------------------------------------------------------------------------------------
	//						consumer Billing
	//-------------------------------------------------------------------------------------------

	//consumerBillingHome
	@Transactional
	@RequestMapping(value = "/consumerBillingHome", method = RequestMethod.GET)
	public String consumerBillingHome(Model model) throws Exception{
		return "pts/consumerBilling/consumerBillingHome"; // Return the same page after form submission
	}

	//download bill
	@Transactional
	@RequestMapping(value = "/downloadBill", method = RequestMethod.GET)
	public String downloadBill(Model model) throws Exception{
		return "pts/consumerBilling/downloadBill"; // Return the same page after form submission
	}

	//=================================================================
	//							Util
	//=================================================================

	//upload file for meter readings=============================================

	@Transactional
	@RequestMapping(value = "/UploadingMeterReadingFileS", method = RequestMethod.POST)
	public ModelAndView UploadingMeterReadingFileS(
			HttpServletRequest request,
			@RequestParam("files") MultipartFile[] files,
			@ModelAttribute("model") CbrsModel model,
			BindingResult bindingResult) throws Exception {
		System.out.println("Processing uploaded files...");
		ModelAndView mo = new ModelAndView("pts/lisenceeBilling/fileUpload", "model", model);
		String division = model.getDivision();       // e.g., "LECO"
		String billCycle = model.getMetercycle();    // e.g., "436"
		String province = model.getProvince();       // e.g., "WPNL"

		// Path for saving ZIP files: D:\Report\<billCycle>\<division>\<province>
		String zipSavePath = PathMMS.getReportPath() + File.separator +
				billCycle + File.separator +
				division + File.separator +
				province;

		// Path for extracting ZIP contents: D:\ExtractedReports\<billCycle>\<division>\<province>
		String extractionPath = PathMMS.getReportPath() + File.separator +
				billCycle + File.separator +
				division ;

		// Ensure ZIP directory exists
		File zipDir = new File(zipSavePath);
		if (!zipDir.exists()) {
			if (!zipDir.mkdirs()) {
				System.err.println("Failed to create ZIP directory: " + zipSavePath);
			//	model.setErrorMessage("Failed to create ZIP directory: " + zipSavePath);
				return mo;
			}
		}

		// Ensure extraction directory exists
		File extractDir = new File(extractionPath);
		if (!extractDir.exists()) {
			if (!extractDir.mkdirs()) {
				System.err.println("Failed to create extraction directory: " + extractionPath);
			//	model.setErrorMessage("Failed to create extraction directory: " + extractionPath);
				return mo;
			}
		}

		boolean filesProcessed = false;
		StringBuilder processingResults = new StringBuilder();

		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String originalFilename = file.getOriginalFilename();
					if (originalFilename == null || originalFilename.trim().isEmpty()) {
						processingResults.append("File has no name. Skipping.\n");
						continue;
					}

					if (!originalFilename.toLowerCase().endsWith(".zip")) {
						processingResults.append("Invalid file type for: ").append(originalFilename).append(". Only ZIP files are allowed.\n");
						continue;
					}

					// Save ZIP file in the ZIP folder
					File zipFile = new File(zipDir, originalFilename);
					file.transferTo(zipFile);
					System.out.println("ZIP file saved to: " + zipFile.getAbsolutePath());
					processingResults.append("ZIP file saved to: " + zipFile.getAbsolutePath() + "\n");

					// Extract contents to the extraction directory
					ZipExtractor.unzip(zipFile.getAbsolutePath(), extractionPath);
					System.out.println("Extracted into: " + extractDir.getAbsolutePath());
					processingResults.append("Extracted into: " + extractDir.getAbsolutePath() + "\n");

					filesProcessed = true;
				} catch (IOException e) {
					e.printStackTrace();
					String errorMsg = "Error processing file: " + file.getOriginalFilename() + " - " + e.getMessage();
					System.err.println(errorMsg);
					processingResults.append(errorMsg + "\n");
				}
			}
		}

		// Populate model for the view
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.setdivisionList(licenseList);
		model.setProvinceList(provinceList);
		mo.addObject("provinceList",
				new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));

		// Add processing results to the model
//		if (filesProcessed) {
//			model.setSuccessMessage("Files processed successfully. ZIP files saved to: " + zipSavePath +
//					" and extracted to: " + extractionPath);
//		} else {
//			model.setErrorMessage("No files were processed. Please check the logs for details.");
//		}
//		model.setProcessingDetails(processingResults.toString());
		return mo;
	}

	//send the bill cycle
	@Transactional
	@RequestMapping(value = "/getBillCycle", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String getBillCycle() throws Exception {
		Long billCycle = meterProcessDao.getCurrentBillCycleNo();
		return billCycle.toString(); // ✅ returns "436" instead of <Long>436</Long>
	}



//	//process all files for a bill cycle=========================================
//	@Transactional
//	@RequestMapping(value = "/processFiles", method = RequestMethod.GET)
//	public String readFiles(
//			@RequestParam("billCycle") String billCycle,
//			@RequestParam("division") String division,
//			@RequestParam("province") String province,
//			Model model) {
//		String fullPath = basePath + billCycle + "\\"+division + "\\" + province + "\\";
//		try {
//
//			//process the files (batch read)------------------------------------------------
//
//			//check for existing batch records
//			Boolean prevLog = meterProcessDao.checkPreviousReadingLogs(Long.parseLong(billCycle), division, province);
//			System.out.println("prev Log: " + prevLog);
//			if(prevLog) {
//				model.addAttribute("msg", "Batch process is already completed");
//				return "pts/comp/processTable";
//			}
//
//			System.out.println("batch processing started");
//			try{
//				excelMeterReader.extractExcelFiles(fullPath, billCycle);
//				List<MeterReading> meterReadingList = excelMeterReader.getProcessedMeterReadings();
//				meterProcessDao.saveMeterReadingList(meterReadingList);// save to DB
//			}catch (Exception e) {
//				System.out.println("Error in batch processing: " + e.getMessage());
//				model.addAttribute("msg", "Unable to process the files : "+e.getMessage());
//				return "pts/comp/processTable";
//			}
//
//			System.out.println("batch processed successfully.");
//
//			//save to LOG summary after batch processing------------------------------------
//			MeterReadingLog mrLog = new MeterReadingLog();
//			mrLog.setBillCycle(meterProcessDao.setBillCycle_relation(Long.parseLong(billCycle)));
//			mrLog.setLicense(meterProcessDao.setLicense_relation(division));
//			mrLog.setProvince(meterProcessDao.setProvince_relation(province));
//			mrLog.setProcessedBy("SYSTEM");
//			mrLog.setProcessedDate(new Date());
//			mrLog.setFilesRead(excelMeterReader.getSuccessFileCount());
//			mrLog.setFiles(excelMeterReader.getSuccessFileCount()+excelMeterReader.getErrorFileCount());
//			if(excelMeterReader.getErrorFileCount() > 0) {
//				mrLog.setStatus("WARN");
//			} else {
//				mrLog.setStatus("OK");
//			}
//			MeterReadingLog MRLog = meterProcessDao.saveMeterReadingLog(mrLog);
//
//			//set MRlog relation for
//			System.out.println("LOG "+MRLog.getReadingLogId()+" saved successfully.");
//
//
//			//Log errors tracking----------------------------------------------------------
//			System.out.println("Error track handling started");
//			List<MeterReadingFileModel> meterReadingFileModelList = excelMeterReader.getMeterReadingFilesModelList();
//			List<MeterReadingErrLog> meterReadingErrLogList = new ArrayList<>();
//
//			for (MeterReadingFileModel meterReadingFileModel : meterReadingFileModelList) {
//				if(Objects.equals(meterReadingFileModel.getStatus(), "ERROR")) {
//					MeterReadingErrLog mrLogError = new MeterReadingErrLog();
//					mrLogError.setMeterReadingLog(MRLog);
//					mrLogError.setSerialNo(meterReadingFileModel.getSerialNo());
//					mrLogError.setCebSerialNo(meterReadingFileModel.getCebSerialNo());
//					mrLogError.setReason("ERROR");
//					mrLogError.setUpdatedDate(new Date());
//					mrLogError.setAttempts(1L);
//					mrLogError.setStatus("UNSOLVED");
//					meterReadingErrLogList.add(mrLogError);
//				}
//			}
//			meterProcessDao.saveMeterReadingLogErrorList(meterReadingErrLogList);
//			System.out.println("LOG ERROR saved successfully.");
//
//			//MeterReading Energy Summary calculation-----------------------------------------------
//			System.out.println("Meter reading Energy Summary calculation started");
//			List<MeterReadingEnergySummary> meterReadingEnergyList = meterProcessDao.calculateMeterReadingEnergy(meterReadingFileModelList, Long.parseLong(billCycle));
//			meterProcessDao.saveMeterReadingEnergy(meterReadingEnergyList);
//			System.out.println("Meter reading Energy Summary saved successfully.");
//
////			//total energy calculation-----------------------------------------------
////			System.out.println("Total Energy calculation started");
////			List<EnergySummary> totalEnergyList = meterProcessDao.CalculateTotalEnergy(meterReadingList, Long.parseLong(billCycle), division, province);
////			meterProcessDao.saveTotalEnergy(totalEnergyList);
////			System.out.println("Total Energy saved successfully");
//
//			//returning result object-----------------------------------------------------
//			model.addAttribute("processSummary",meterProcessDao.getMeterReadingFileModelList());
//
//			//model.addAttribute("total",modelService.getAllTotals(totalEnergyList));
//			return "pts/comp/processTable";
//
//		} catch (Exception e) {
//			System.out.println("Endpoint error occurred: " + e.getMessage());
//			model.addAttribute("msg", "Unable to process the files : "+e.getMessage());
//			return "pts/comp/processTable";
//		}
//	}

//	//upload error files to reprocess=========================================
//	@Transactional
//	@ResponseBody
//	@RequestMapping(value = "/reProcess", method = RequestMethod.POST)
//	public ResponseEntity<String> checkFileUpload(
//			@RequestParam("file") MultipartFile file,
//			@RequestParam("billCycle") String billCycle
//	) {
//		String uploadDir = "C:\\Users\\Dhammika Mahendra\\Documents\\CEB Projects\\PowerTrading_resourcefile\\Corrections";
//		try {
//			if (file.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
//			}
//			String fileName = file.getOriginalFilename();
//			java.nio.file.Path path = java.nio.file.Paths.get(uploadDir, fileName);
//			file.transferTo(path.toFile());
//			System.out.println("File saved: " + path);
//			excelMeterReader.ReProcessFile(path.toString(), Long.parseLong(billCycle));
//			return ResponseEntity.ok("File uploaded successfully: " + fileName);
//		} catch (Exception e) {
//			System.out.println("An error occurred: " + e.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//		}
//	}
//
//
//	//viewMeterReadingList===================================================
//	@Transactional(readOnly = true)
//	@RequestMapping(value = "/viewMeterReadingList", method = RequestMethod.GET)
//	public String getMeterPointAndReadings(
//			@RequestParam("billCycle") Long billCycle,
//			@RequestParam("division") String division,
//			@RequestParam("province") String province,
//			Model model) throws Exception {
//		System.out.println("getMeterPointAndReadings method called");
//		List<MeterPointModel> meterPoints = new ArrayList<>();
//		try {
//			List<MeterReadingRecordModel> meterReadings = meterReadingDao.getMeterReadings(billCycle, province, division);
//			meterPoints = meterReadingDao.getMeterPoints(province, division);
//
//			// Matching energy summary values
//			List<String> serialNumbers = meterPoints.stream()
//					.map(MeterPointModel::getSerialNo)
//					.collect(Collectors.toList());
//
//			List<MeterReadingEnergySummary> energySummaries = meterReadingDao.getMeterReadingEnergySummary(billCycle, serialNumbers);
//
//			Map<String, MeterReadingEnergySummary> energySummaryMap = energySummaries.stream()
//					.collect(Collectors.toMap(MeterReadingEnergySummary::getSerialNo, summary -> summary));
//
//			// Attach readings and energy summaries to meter points
//			for (MeterReadingRecordModel reading : meterReadings) {
//				for (MeterPointModel point : meterPoints) {
//					if (point.getId().equals(reading.getPointId())) {
//						point.getMeterReadingRecordModels().add(reading);
//						point.setStatus("Complete");
//
//						MeterReadingEnergySummary summary = energySummaryMap.get(point.getSerialNo());
//						if (summary != null) {
//							point.setImportEnergy(summary.getImportEnergy());
//							point.setExportEnergy(summary.getExportEnergy());
//							point.setCoincidentPeak(summary.getCoincidentPeak());
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new RuntimeException("An error occurred while fetching the records.");
//		}
//
//		//loading the error meter points-------------------------------
//		if(!meterPoints.isEmpty()){
//			List<String> errorMeterList = meterReadingDao.getErrorMeterList(billCycle,division,province);
//			//checking the error meters present in the meterPoints with above errorMeterList
//			for (String errorSerialNo : errorMeterList) {
//				for (MeterPointModel point : meterPoints) {
//					if (errorSerialNo.equals(point.getCebSerialNo())) {
//						point.setStatus("Error");
//					}
//				}
//			}
//		}
//
//		model.addAttribute("thisBillCycle",billCycle);
//		model.addAttribute("meterReadingFileList", meterPoints);
//
//		return "pts/comp/meterReadingTable";
//	}
//


}
