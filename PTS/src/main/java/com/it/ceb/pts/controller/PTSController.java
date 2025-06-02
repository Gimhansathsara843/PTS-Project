package com.it.ceb.pts.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;


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


import org.apache.commons.compress.utils.IOUtils;
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
	@Autowired
	private com.it.ceb.pts.repo.FileUploadHeaderDao fileUploadHeaderDao;



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
	public ModelAndView uploadFile(@ModelAttribute("model") FileUploadModel fileUploadModel) throws Exception{
		System.out.println("init jsp");
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		fileUploadModel.setLicenseList(licenseList);
		fileUploadModel.setProvinceList(provinceList);
		ModelAndView mv= new ModelAndView("pts/lisenceeBilling/fileUpload", "model", fileUploadModel);
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
	public ModelAndView uploadingMeterReadingFileS(
			HttpServletRequest request,
			@RequestParam("files") MultipartFile[] files,
			@ModelAttribute("model") FileUploadModel model,
			BindingResult bindingResult) throws Exception {

		System.out.println("submit jsp");
		ModelAndView mo = new ModelAndView("pts/lisenceeBilling/fileUpload", "model", model);

		String division = model.getDivision();
		String billCycle = model.getBillCycle();
		String province = model.getProvince();

		String zipSavePath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province;
		String extractionPath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division;

		boolean filesProcessed = false;
		StringBuilder processingResults = new StringBuilder();
		boolean hasValidZipFiles = false;

		// First, check if there are any valid ZIP files
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				if (originalFilename != null && !originalFilename.trim().isEmpty() &&
						originalFilename.toLowerCase().endsWith(".zip")) {
					hasValidZipFiles = true;
					break;
				}
			}
		}

		// Only create directories if there are valid ZIP files
		File zipDir = null;
		File extractDir = null;

		if (hasValidZipFiles) {
			zipDir = new File(zipSavePath);
			if (!zipDir.exists() && !zipDir.mkdirs()) {
				mo.addObject("msg", "Failed to create ZIP directory: " + zipSavePath);
				return mo;
			}

			extractDir = new File(extractionPath);
			if (!extractDir.exists() && !extractDir.mkdirs()) {
				mo.addObject("msg", "Failed to create extraction directory: " + extractionPath);
				return mo;
			}
		}

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

					//Check if file already exists in database
//					if (isFileAlreadyUploaded(billCycle, division, province, originalFilename)) {
//						processingResults.append("File already uploaded: ").append(originalFilename).append(" - Skipping.\n");
//						continue;
//					}

//					// Save ZIP file (directories are already created at this point)
//					File zipFile = new File(zipDir, originalFilename);
//					file.transferTo(zipFile);
//					processingResults.append("ZIP file saved to: ").append(zipFile.getAbsolutePath()).append("\n");
//
//					// Extract
//					ZipExtractor.unzip(zipFile.getAbsolutePath(), extractionPath);

					String uniqueFilename = originalFilename;

					//Check for duplicates
					if (isFileAlreadyUploaded(billCycle, division, province, originalFilename)) {
						model.setFileNameConflict(true); // Optional: use for JSP confirmation
						model.setOriginalFileName(originalFilename);

						// Generate a unique name like file.zip → file1.zip, file2.zip, etc.
						uniqueFilename = generateUniqueFileName(originalFilename, billCycle, division, province);
						processingResults.append("Duplicate found. File renamed to: ").append(uniqueFilename).append("\n");
						System.out.println("+++++++++++++++++++++++++++++++" + uniqueFilename);
						mo.addObject("msg", "Duplicate file found: " + originalFilename + ". Renamed to: " + uniqueFilename);
					}

					//Save the renamed ZIP file
					File zipFile = new File(zipDir, uniqueFilename);
					file.transferTo(zipFile);
					processingResults.append("ZIP file saved to: ").append(zipFile.getAbsolutePath()).append("\n");

					// Extract
					ZipExtractor.unzip(zipFile.getAbsolutePath(), extractionPath);
					processingResults.append("Extracted into: ").append(extractionPath).append("\n");

					System.out.println("====================enter to the save method================ ");

						try {
							// Create the composite primary key
							FileUploadHeader header = new FileUploadHeader();
							header.setBillCycleNo(Long.parseLong(billCycle));
							header.setLicenseCode(division);
							header.setProvinceCode(province);

							header.setFileName(uniqueFilename); // Use the unique filename
							header.setIsUploaded(1L); // 1 = uploaded successfully
							header.setUploadedBy(getUserName(request));
							header.setUploadedDate(LocalDate.now());
							header.setFileType("ZIP");
							//header.setUploadId(fileUploadHeaderDao.generateNextUploadId()); // Generate next upload ID
							// Save using DAO
							fileUploadHeaderDao.save(header);

							System.out.println("File metadata saved successfully for: in the try catch " + originalFilename);

						//	mo.addObject("msg", "File metadata saved successfully for: " + originalFilename);

						//	model.setSuccessMessage("File metadata saved successfully for: " + originalFilename);


						} catch (NumberFormatException e) {
							throw new RuntimeException("Invalid bill cycle number: " + billCycle, e);
						} catch (Exception e) {
						//	throw new RuntimeException("Failed to save file metadata: " + e.getMessage(), e);

							//e.printStackTrace();
						}
						System.out.println("=========================Successfully=============================");

					System.out.println("**************terminate the save method****************** ");
					processingResults.append("File metadata saved to database for: ").append(originalFilename).append("\n");

					filesProcessed = true;

				} catch (IOException e) {
					processingResults.append("Error processing file: ").append(file.getOriginalFilename()).append(" - ").append(e.getMessage()).append("\n");
					//e.printStackTrace();
				} catch (Exception e) {
					processingResults.append("Database error for file: ").append(file.getOriginalFilename()).append(" - ").append(e.getMessage()).append("\n");
					//e.printStackTrace();
				}
			}
		}

		System.out.println("++++++++++++++++   enter to the method   +++++++++++++++");

		if (filesProcessed) {
		//	mo.addObject("msg", "Files processed successfully" + processingResults);
			model.setSuccessMessage("Files processed successfully" );
			System.out.println(model.getSuccessMessage());
			System.out.println("'llllllllllllllllllllllllllllllllllljjjjjjjjj");
		} else {
			//mo.addObject("msg", "No files were processed" + processingResults);
			model.setErrorMessage("No files were processed: " + processingResults);
			System.out.println(model.getErrorMessage());
			System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		}

		// Repopulate dropdowns
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.setLicenseList(licenseList);
		model.setProvinceList(provinceList);
		mo.addObject("provinceList", new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));

		System.out.println("+++++++++++++++++++++++++++++++" + model.getProvinceList());
		return mo ; // Return the same page after form submission
	}

	// Add this method in your controller or service
	private String generateUniqueFileName(String baseName, String billCycle, String division, String province) {
		String nameWithoutExt = baseName;
		String extension = "";

		int dotIndex = baseName.lastIndexOf('.');
		if (dotIndex > 0) {
			nameWithoutExt = baseName.substring(0, dotIndex);
			extension = baseName.substring(dotIndex); // includes the dot
		}

		String candidate = baseName;
		int counter = 1;
		while (isFileAlreadyUploaded(billCycle, division, province, candidate)) {
			candidate = nameWithoutExt + counter + extension;
			counter++;
		}

		return candidate;
	}
	/**
	 * Check if file is already uploaded to prevent duplicates
	 */

	public boolean isFileAlreadyUploaded(String billCycle, String division, String province, String fileName) {
		try {
			// Use DAO to check for existing file
			FileUploadHeader existingHeader = fileUploadHeaderDao.findByCompositeKeyAndFileName(
					Long.parseLong(billCycle), division, province, fileName);

			return existingHeader != null;

		} catch (Exception e) {
			System.err.println("Error checking for existing file: " + e.getMessage());
			return false; // If error checking, allow upload to proceed
		}
	}

	/**
	 * Get username from request, with fallback to default
	 */

	public String getUserName(HttpServletRequest request) {
		try {
			if (request.getUserPrincipal() != null) {
				String username = request.getUserPrincipal().getName();
				// Ensure username fits in database column (max 15 chars)
				return username.length() > 15 ? username.substring(0, 15) : username;
			}
		} catch (Exception e) {
			System.err.println("Error getting username: " + e.getMessage());
		}
		return "system"; // Default fallback
	}


	//send the bill cycle
	@Transactional
	@RequestMapping(value = "/getBillCycle", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String getBillCycle() throws Exception {
		Long billCycle = meterProcessDao.getCurrentBillCycleNo();
		return billCycle.toString(); // returns "436" instead of <Long>436</Long>
	}

//	@RequestMapping(value = "/getUploadedFiles", method = RequestMethod.GET)
//	@ResponseBody
//	public List<String> getUploadedFiles(@RequestParam("billCycle") String billCycle,
//										 @RequestParam("division") String division,
//										 @RequestParam("province") String province) {
//
//		String fileDirectory = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province;
//		File dir = new File(fileDirectory);
//
//		List<String> fileNames = new ArrayList<>();
//		if (dir.exists() && dir.isDirectory()) {
//			File[] files = dir.listFiles();
//			if (files != null) {
//				for (File file : files) {
//					if (file.isFile()) {
//						fileNames.add(file.getName());
//					}
//				}
//			}
//		}
//
//		System.out.println("return the get upload files method ");
//		return fileNames;
//	}
//
//	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
//	public void downloadFile(@RequestParam("fileName") String fileName,
//							 @RequestParam("billCycle") String billCycle,
//							 @RequestParam("division") String division,
//							 @RequestParam("province") String province,
//							 HttpServletResponse response) throws IOException {
//
//		String filePath = PathMMS.getReportPath() + File.separator + billCycle + File.separator + division + File.separator + province + File.separator + fileName;
//		File file = new File(filePath);
//
//		if (file.exists()) {
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
//
//			FileInputStream fis = new FileInputStream(file);
//			IOUtils.copy(fis, response.getOutputStream());
//			response.flushBuffer();
//			fis.close();
//		} else {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//		}
//
//		System.out.println("return the download file method ");
//	}




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
