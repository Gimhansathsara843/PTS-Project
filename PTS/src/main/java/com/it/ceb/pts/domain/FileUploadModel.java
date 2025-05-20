package com.it.ceb.pts.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class FileUploadModel {
	
	private List<FileUpload> fileList;
	List<DistributionLicense> licenseList;
	List<Province> provinceList;
	
	private List<MeterReading> meterReadingList;
	
	public List<MeterReading> getMeterReadingList() {
		return meterReadingList;
	}

	public void setMeterReadingList(List<MeterReading> meterReadingList) {
		this.meterReadingList = meterReadingList;
	}

	public List<FileUpload> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileUpload> fileList) {
		this.fileList = fileList;
	}

	

	

	public List<DistributionLicense> getLicenseList() {
		return licenseList;
	}

	public void setLicenseList(List<DistributionLicense> licenseList) {
		this.licenseList = licenseList;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}





	public String filetype;
	
	
	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String division;
	
	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}
	
	public String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String area;
	

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String grid;
	
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}
	
	public String meterpoint;

	public String getMeterpoint() {
		return meterpoint;
	}

	public void setMeterpoint(String meterpoint) {
		this.meterpoint = meterpoint;
	}

	public String billCycle;

	public String getBillCycle() {
		return billCycle;
	}

	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}
	
  

    
	
	
	
	
	
	
	

}
