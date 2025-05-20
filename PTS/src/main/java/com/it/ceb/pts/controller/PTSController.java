package com.it.ceb.pts.controller;


import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.ceb.pts.domain.*;
import com.it.ceb.pts.repo.*;
import com.it.ceb.util.common.ExcelMeterReader;
import com.it.ceb.util.common.model.*;

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

	private String basePath ="D:\\GIT\\PTS_NEW\\ReadingFiles\\ProcessFiles\\";

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
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public ModelAndView uploadFile(@ModelAttribute("FileUploadModel") FileUploadModel fileUploadModel, HttpServletRequest request) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		fileUploadModel.setProvinceList(provinceList);
		fileUploadModel.setLicenseList(licenseList);
		return new ModelAndView("pts/lisenceeBilling/readingFileUpload", "FileUploadModel", fileUploadModel);
	}

	//view upload meter points
	@Transactional
	@RequestMapping(value = "/viewUploadFile", method = RequestMethod.GET)
	public String viewMeterPoints(Model model) throws Exception{
		List<DistributionLicense> licenseList = DistributionLicenseDao.getLicenseList();
		List<Province> provinceList = provinceDao.getAllProvince();
		model.addAttribute("licenseList",licenseList);
		model.addAttribute("provinceList",new ObjectMapper().writeValueAsString(modelService.getAllProvinces(provinceList)));
		return "pts/lisenceeBilling/viewFileUpload"; // Return the same page after form submission
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


	//process all files for a bill cycle=========================================
	@Transactional
	@RequestMapping(value = "/processFiles", method = RequestMethod.GET)
	public String readFiles(
			@RequestParam("billCycle") String billCycle,
			@RequestParam("division") String division,
			@RequestParam("province") String province,
			Model model) {
		String fullPath = basePath + billCycle + "\\"+division + "\\" + province + "\\";
		System.out.println("fullPath"+fullPath);
		try {

			//process the files (batch read)------------------------------------------------

			//check for existing batch records
			Boolean prevLog = meterProcessDao.checkPreviousReadingLogs(Long.parseLong(billCycle), division, province);
			System.out.println("prev Log: " + prevLog);
			if(prevLog) {
				model.addAttribute("msg", "Batch process is already completed");
				return "pts/comp/processTable";
			}

			System.out.println("batch processing started");
			excelMeterReader.extractExcelFiles(fullPath, billCycle);
			List<MeterReading> meterReadingList = excelMeterReader.getProcessedMeterReadings();
			meterProcessDao.saveMeterReadingList(meterReadingList);// save to DB
			System.out.println("batch processed successfully.");

			//save to LOG summery after batch processing------------------------------------
			MeterReadingLog mrLog = new MeterReadingLog();
			mrLog.setBillCycle(meterProcessDao.setBillCycle_relation(Long.parseLong(billCycle)));
			mrLog.setLicense(meterProcessDao.setLicense_relation(division));
			mrLog.setProvince(meterProcessDao.setProvince_relation(province));
			mrLog.setProcessedBy("SYSTEM");
			mrLog.setProcessedDate(new Date());
			mrLog.setFilesRead(excelMeterReader.getSuccessFileCount());
			mrLog.setFiles(excelMeterReader.getSuccessFileCount()+excelMeterReader.getErrorFileCount());
			if(excelMeterReader.getErrorFileCount() > 0) {
				mrLog.setStatus("WARN");
			} else {
				mrLog.setStatus("OK");
			}
			MeterReadingLog MRLog = meterProcessDao.saveMeterReadingLog(mrLog);

			//set MRlog relation for
			System.out.println("LOG "+MRLog.getReadingLogId()+" saved successfully.");


			//Log errors tracking----------------------------------------------------------
			System.out.println("Error track handling started");
			List<MeterProcessFileModel> meterProcessFileModelList = excelMeterReader.getMeterProcessFilesDTOList();
			List<MeterReadingErrLog> meterReadingErrLogList = new ArrayList<>();

			for (MeterProcessFileModel meterProcessFileModel : meterProcessFileModelList) {
				if(Objects.equals(meterProcessFileModel.getStatus(), "ERROR")) {
					MeterReadingErrLog mrLogError = new MeterReadingErrLog();
					mrLogError.setMeterReadingLog(MRLog);
					mrLogError.setSerialNo(meterProcessFileModel.getSerialNo());
					mrLogError.setCebSerialNo(meterProcessFileModel.getCebSerialNo());
					mrLogError.setReason("ERROR");
					mrLogError.setUpdatedDate(new Date());
					mrLogError.setAttempts(1L);
					mrLogError.setStatus("UNSOLVED");
					meterReadingErrLogList.add(mrLogError);
				}
			}
			meterProcessDao.saveMeterReadingLogErrorList(meterReadingErrLogList);
			System.out.println("LOG ERROR saved successfully.");

			//total energy calculation-----------------------------------------------
			System.out.println("Total Energy calculation started");
			List<EnergySummary> totalEnergyList = meterProcessDao.CalculateTotalEnergy(meterReadingList, Long.parseLong(billCycle), division, province);
			meterProcessDao.saveTotalEnergy(totalEnergyList);
			System.out.println("Total Energy saved successfully.");

			//returning result object-----------------------------------------------------
			model.addAttribute("processSummary",meterProcessFileModelList);
			model.addAttribute("total",modelService.getAllTotals(totalEnergyList));
			return "pts/comp/processTable";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Unable to process the files. Please try again."+e.getMessage());
			return "pts/comp/processTable";
		}
	}

	//upload error files to reprocess=========================================
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/reProcess", method = RequestMethod.POST)
	public ResponseEntity<String> checkFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("billCycle") String billCycle
			) {
		String uploadDir = "C:\\Users\\Dhammika Mahendra\\Documents\\CEB Projects\\PowerTrading_resourcefile\\Corrections";
		try {
			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
			}
			String fileName = file.getOriginalFilename();
			java.nio.file.Path path = java.nio.file.Paths.get(uploadDir, fileName);
			file.transferTo(path.toFile());
			System.out.println("File saved: " + path);
			excelMeterReader.ReProcessFile(path.toString(), Long.parseLong(billCycle));
			return ResponseEntity.ok("File uploaded successfully: " + fileName);
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	//selected meter reading record results====================================

	@ResponseBody
	@Transactional(readOnly = true)
	@RequestMapping(value = "/viewErr", method = RequestMethod.GET, produces = "application/json")
	public String viewErr(
			@RequestParam("billCycle") String billCycle,
			@RequestParam("division") String division,
			@RequestParam("province") String province) throws Exception {
		System.out.println("viewMeterReadings method called");
		List<MeterReadingErrorResultModel> meterReadingList = meterReadingDao.getMeterReadingErrors(Long.parseLong(billCycle), division, province);
		System.out.println("meter reading query executed");
		return new ObjectMapper().writeValueAsString(meterReadingList);
	}

	@Transactional(readOnly = true)
	@ResponseBody
	@RequestMapping(value = "/view", method = RequestMethod.GET, produces = "application/json")
	public List<MeterReadingResultModel> view(
			@RequestParam("billCycle") String billCycle,
			Model model) throws Exception {
		System.out.println("viewMeterReadings method called");
		List<MeterReadingResultModel> meterReadingList = meterReadingDao.getMeterReadingsFullList(Long.parseLong(billCycle));
		//loop print of meterReadingList
		for (MeterReadingResultModel meterReading : meterReadingList) {
			System.out.println(meterReading.getCurrentReading());
		}
		System.out.println("meter reading query executed");
		return meterReadingList;
	}

	@Transactional
	@ResponseBody
	@RequestMapping(value = "/viewMRList", method = RequestMethod.GET, produces = "application/json")
	public String viewMeterReadings(
			@RequestParam("billCycle") String billCycle,
			@RequestParam("division") String division,
			@RequestParam("province") String province,
			Model model) throws Exception {
		System.out.println("reached");
		List<MeterReadingResultModel> meterReadingList = meterReadingDao.getMeterReadingsFull(Long.parseLong(billCycle), division, province);
		for (MeterReadingResultModel reading : meterReadingList) {
			System.out.println("Reading ID List: " + reading.getReadingId());
		}
		List<MeterReadingFileModel> fileModels = modelService.convertToMeterReadingFilemodel(meterReadingList);
		model.addAttribute("meterReadingFileList", fileModels);
		return new ObjectMapper().writeValueAsString(fileModels);
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/viewMeterReadingList", method = RequestMethod.GET)
	public String getMeterPointAndReadings(
			@RequestParam("billCycle") Long billCycle,
			@RequestParam("division") String division,
			@RequestParam("province") String province,
			Model model) throws Exception {
		System.out.println("getMeterPointAndReadings method called");
		List<MeterPointModel> meterPoints = new ArrayList<>();
		try {
			List<MeterReadingRecordModel> meterReadings = meterReadingDao.getMeterReadings(billCycle, province, division);
			meterPoints = meterReadingDao.getMeterPoints(province, division);
			for (MeterReadingRecordModel reading : meterReadings) {
				for (MeterPointModel point : meterPoints) {
					if (point.getId().equals(reading.getPointId())) { // Assuming pointId is the linking property
						point.getMeterReadingModels().add(reading);
						point.setStatus("Complete");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("An error occurred while fetching the records.");
		}

		//loading the error meter points-------------------------------
		if(!meterPoints.isEmpty()){
			List<String> errorMeterList = meterReadingDao.getErrorMeterList(billCycle,division,province);
			//checking the error meters present in the meterPoints with above errorMeterList
			for (String errorSerialNo : errorMeterList) {
				for (MeterPointModel point : meterPoints) {
					if (errorSerialNo.equals(point.getCebSerialNo())) {
						point.setStatus("Error");
					}
				}
			}
		}

		model.addAttribute("thisBillCycle",billCycle);
		model.addAttribute("meterReadingFileList", meterPoints);

		return "pts/comp/meterReadingTable";

	}

}
