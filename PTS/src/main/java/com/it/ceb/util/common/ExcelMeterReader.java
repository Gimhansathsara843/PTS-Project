package com.it.ceb.util.common;

import com.it.ceb.pts.domain.*;
import com.it.ceb.pts.repo.MeterProcessDao;
import com.it.ceb.util.common.model.MeterProcessFileModel;
import com.it.ceb.util.common.model.MeterProcessRecordModel;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ExcelMeterReader {

    @Autowired
    private MeterProcessDao meterDao;

    private static final List<String> EXCEL_EXTENSIONS = Arrays.asList("xls", "xlsx");
    private List<String> energyPrefixes;
    private Long billCycle;
    private Long successCount = 0L;
    private Long errorCount = 0L;
    private List<MeterProcessFileModel> meterProcessFileDTOList = new ArrayList<>();

    private List<MeterReading> meterReadings;

    @PostConstruct
    private void init() {
        this.energyPrefixes = meterDao.getEnergyPrefixes();
    }


    //===================================================================================
    //                             Batch process of excel files
    //===================================================================================

    //Entering method------------------------------------------------------
    @Transactional
    public void extractExcelFiles(String folderPath,String billCycle) throws IllegalArgumentException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid path (folder)");
        }
        this.billCycle = Long.parseLong(billCycle);
        this.successCount = 0L;
        this.errorCount = 0L;
        this.meterProcessFileDTOList = new ArrayList<>();
        this.meterReadings = new ArrayList<>();
        searchExcelFiles(folder);
    }

    //Iterate and explore folders (like deep search)------------------------
    private void searchExcelFiles(File folder) throws IllegalArgumentException {

        FileFilter excelFilter = file -> {
            if (file.isDirectory()) return true;
            String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();
            if (!EXCEL_EXTENSIONS.contains(extension)) return false;

            String nameWithoutExtension = FilenameUtils.removeExtension(file.getName());
            String[] parts = nameWithoutExtension.split("-");
            if (parts.length != 2) return false;

            return energyPrefixes.contains(parts[1]);
        };

        File[] files = folder.listFiles(excelFilter);

        if (files == null) {
            System.out.println("Unable to access directory: " + folder.getPath());
            throw new IllegalArgumentException("Invalid path (access denied)");
        }

        // loop for every files of a folder
        for (File file : files) {
            if (file.isDirectory()) {
                searchExcelFiles(file);
            } else {
                processFile(file, billCycle);
            }
        }
    }


    //===================================================================================
    //                            Re process of excel files
    //===================================================================================

    //for reprocessing error files
    @Transactional
    public void ReProcessFile(String filePath, Long billCycle) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid path (file)");
        }

        MeterReadingErrLog errLog;
        //check for already existence
        String nameWithoutExtension = FilenameUtils.removeExtension(file.getName());
        String[] parts = nameWithoutExtension.split("-");
        try{
            errLog = meterDao.checkBeforeReProcess(parts[0],billCycle);
            if (errLog == null) {
                throw new RuntimeException("No error log found for : " + parts[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("An error occurred while finding error log for : "+parts[0]);
        }

        //Add data to meter reading
        this.billCycle = billCycle;
        this.successCount = 0L;
        this.errorCount = 0L;
        this.meterProcessFileDTOList = new ArrayList<>();
        this.meterReadings = new ArrayList<>();
        processFile(file, billCycle);
        meterDao.saveMeterReadingList(this.getProcessedMeterReadings());
        //Update readfiles count in reading log
        meterDao.updateMeterReadingLog_FileRead(errLog.getMeterReadingLog().getReadingLogId());

        //Update error log record
        errLog.setStatus("SOLVED");
        errLog.setAttempts(errLog.getAttempts() + 1);
        meterDao.updateMeterReadingErrLog(errLog);
    }


    //=====================================================================================
    //                                        Helper methods
    //=====================================================================================

    //------------------------     for excel files     ------------------------------------

    //file access and reading------------------------------
    private void processFile(File file, Long billCycle) {

        //properties associated with a "file"
        String nameWithoutExtension = FilenameUtils.removeExtension(file.getName());//SerialNo
        String serialNo = nameWithoutExtension.split("-")[0];

        MeterPoint meterPoint = meterDao.getMeterPointBySerialNo(serialNo);//which model
        if(meterPoint!=null)
        {
	        							Long modelId = meterPoint.getMeter().getMeterHeader().getMeterModel().getModelId();
	
	        List<MeasureCell> cellAddr= meterDao.getMeasureCellById(modelId.toString());//MeasureCells of a model
	
	        Map<Long,BigDecimal> cellReadings;//(measureId + reading) value pairs
	
	        //MeterProcessFile details [FOR RESULT SET]
	        MeterProcessFileModel meterProcessFileModel = new MeterProcessFileModel();
	        meterProcessFileModel.setSerialNo(serialNo);
	        meterProcessFileModel.setCebSerialNo(meterPoint.getMeter().getCebSerialNo());
	        meterProcessFileModel.setFileName(file.getName());
	
	        try{
	            //read excel file content to extract values---------------------------------
	            cellReadings = readCellValues(cellAddr, file);
	
	            List<MeterReading> prevMeterReadings = new ArrayList<>();
	
	            try {
	                prevMeterReadings = meterDao.getPreviousReading(serialNo, billCycle);//for previous readings
	            } catch (Exception e) {
	                System.out.println(e.getMessage());
	                e.printStackTrace();
	            }
	
	            //---------------------------------------------------------------------
	            //                     preparing for database saving
	            //---------------------------------------------------------------------
	
	            //Preparing relational entities
	            BillCycle bc = meterDao.setBillCycle_relation(billCycle);
	
	            //only to store this round meter readings
	            List<MeterReading> meterReadingsTemp = new ArrayList<>();
	
	            //Create MeterReading objects loop-wise for the measureId set
	            assert cellReadings != null;
	            for (Map.Entry<Long, BigDecimal> entry : cellReadings.entrySet()) {
	                Long measureId = entry.getKey();
	                BigDecimal reading = entry.getValue();
	
	                MeterReading meterReading = new MeterReading();
	                meterReading.setCurrentReading(reading);
	                meterReading.setBillCycle(bc);
	                meterReading.setMeter(meterPoint.getMeter());
	                meterReading.setSerialNo(serialNo);
	                meterReading.setCreatedBy("SYSTEM");
	                meterReading.setCreatedDate(new Date());
	                meterReading.setPoint(meterPoint);
	
	                BigDecimal prevRead = meterDao.matchPreviousReading(measureId, prevMeterReadings);
	                meterReading.setPreviousReading(prevRead);
	                meterReading.setEnergy(prevRead.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : reading.subtract(prevRead));
	                Measure msr = meterDao.setMeasure_relation(measureId);
	                meterReading.setMeasure(msr);
	
	                meterReading.setReadingMethod("AUTO");
	                meterReadings.add(meterReading);//whole measurement set
	                meterReadingsTemp.add(meterReading);//only for this round (measurement set)
	            }
	
	
	            //Meter process record details of MeterProcessFile details [FOR RESULT VIEW - font end]
	
	            List<MeterProcessRecordModel> meterProcessRecordModelList = new ArrayList<>();
	            for (MeterReading meterReading : meterReadingsTemp) {
	                MeterProcessRecordModel meterProcessRecordModel = new MeterProcessRecordModel();
	                meterProcessRecordModel.setReadingId(meterReading.getReadingId());
	                meterProcessRecordModel.setMeasureName(meterReading.getMeasure().getMeasureName());
	                meterProcessRecordModel.setCurrentReading(meterReading.getCurrentReading());
	                meterProcessRecordModel.setPreviousReading(meterReading.getPreviousReading());
	                meterProcessRecordModel.setEnergy(meterReading.getEnergy());
	                meterProcessRecordModelList.add(meterProcessRecordModel);
	            }
	            meterProcessFileModel.setPss(meterPoint.getPrimarySubstation().getPssName());
	            meterProcessFileModel.setMeterProcessRecordModelList(meterProcessRecordModelList);
	            meterProcessFileModel.setStatus("SUCCESS");
	            meterProcessFileDTOList.add(meterProcessFileModel);
	
	            successCount++;
	
	        }catch (Exception e){
	            errorCount++;
	            meterProcessFileModel.setStatus("ERROR");
	            meterProcessFileDTOList.add(meterProcessFileModel);
	            System.out.println("Error reading excel file: " + e.getMessage());
	            e.printStackTrace();
	        }
        }//meter point is not null
    }

    //Cell value reading ----------------------------------
    private Map<Long, BigDecimal> readCellValues(List<MeasureCell> cellAddresses, File file) {
        Map<Long, BigDecimal> readings = new HashMap<>();
        String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook;

            try {
                if ("xlsx".equals(extension)) {
                    workbook = new XSSFWorkbook(fis);
                } else if ("xls".equals(extension)) {
                    workbook = new HSSFWorkbook(fis);
                } else {
                    throw new IllegalArgumentException("Unsupported file format. Only .xls and .xlsx are supported");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error creating workbook: " + e.getMessage());
            }

            try (workbook) {
                Sheet sheet = workbook.getSheetAt(0); // Assuming first sheet
                for (MeasureCell cell : cellAddresses) {
                    BigDecimal val;
                    if (cell.getCell().matches("[A-Z]\\d+")) {
                        val = CurrentReadingCalc(readSpecificCell(sheet, cell.getCell()), cell.getValueCalc());
                    } else if (cell.getCell().matches("[A-Z]")) {
                        val = CurrentReadingCalc(readLastCellInColumn(sheet, cell.getCell()), cell.getValueCalc());
                    } else {
                        throw new IllegalArgumentException("Invalid cell address format");
                    }

                    //make import negative
                    if(Objects.equals(cell.getMeasure().getImportOrExport(), "I")){
                        val = val.multiply(new BigDecimal(-1));
                    }
                    readings.put(cell.getMeasureId(), val);
                }
                return readings;
            }catch (Exception e){
                throw new IllegalArgumentException("Abnormal/corrupted cells: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error while file stream ops: " + e.getMessage());
        }
    }
    private BigDecimal CurrentReadingCalc(String value, String operator){
        try {
            if (operator == null || operator.trim().isEmpty()) {
                return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP);
            }

            value = value.trim();
            operator = operator.trim();
            //float numericValue = Float.parseFloat(value);
            String op = operator.substring(0, 1);
            float operand = Float.parseFloat(operator.substring(1));

            BigDecimal result = switch (op) {
                case "*" -> new BigDecimal(value).multiply(new BigDecimal(operand));
                case "/" -> new BigDecimal(value).divide(new BigDecimal(operand), RoundingMode.HALF_UP);
                default -> throw new IllegalArgumentException("Invalid operator: " + op);
            };

            return result.setScale(0, RoundingMode.HALF_UP);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid numeric format", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error in calculation", e);
        }
    }

    //type of cell to be read-------------------------------
    private String readSpecificCell(Sheet sheet, String cellAddress) {
        CellReference cellReference = new CellReference(cellAddress);
        Row row = sheet.getRow(cellReference.getRow());
        if (row == null) return null;

        Cell cell = row.getCell(cellReference.getCol());
        return getCellValueAsString(cell);
    }
    private String readLastCellInColumn(Sheet sheet, String columnLetter) {
        int columnIndex = CellReference.convertColStringToIndex(columnLetter);
        String lastValue = null;

        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                String value = getCellValueAsString(cell);
                if (value != null && !value.trim().isEmpty()) {
                    lastValue = value;
                }
            }
        }
        return lastValue;
    }

    //cell value retrieval---------------------------------
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                String stringValue = cell.getStringCellValue();
                return stringValue != null ? stringValue.trim() : null;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                }
                // Handle integers without decimal points
                double numericValue = cell.getNumericCellValue();
                if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
                    return String.format("%.0f", numericValue);
                }
                // Handle other numeric values with exact precision
                return new BigDecimal(String.valueOf(numericValue))
                        .stripTrailingZeros()
                        .toPlainString();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return getFormulaResult(cell);
                } catch (Exception e) {
                    e.printStackTrace();
                    return cell.getCellFormula();
                }
            case BLANK:
                return "";
            case ERROR:
                return "ERROR: " + cell.getErrorCellValue();
            default:
                return null;
        }
    }
    private String getFormulaResult(Cell cell) {
        switch (cell.getCachedFormulaResultType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getNumericCellValue());
                }
                double value = cell.getNumericCellValue();
                if (value == Math.floor(value) && !Double.isInfinite(value)) {
                    return String.format("%.0f", value);
                }
                return new BigDecimal(String.valueOf(value))
                        .stripTrailingZeros()
                        .toPlainString();
            case STRING:
                String strValue = cell.getStringCellValue();
                return strValue != null ? strValue.trim() : null;
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return cell.getCellFormula();
        }
    }


    //------------------------     for meter reading ops     ------------------------------

    public Long getSuccessFileCount() {return this.successCount;}
    public Long getErrorFileCount() {return this.errorCount;}
    public List<MeterProcessFileModel> getMeterProcessFilesDTOList() { return this.meterProcessFileDTOList; }
    public List<MeterReading> getProcessedMeterReadings() {return this.meterReadings;}

}
