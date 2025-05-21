package com.it.ceb.pts.repo;

import com.it.ceb.pts.domain.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MeterProcessDao {

    List<String> getEnergyPrefixes();
    Meter getMeterBySerialNo(String serialNo);
    MeterPoint getMeterPointBySerialNo(String serialNo);
    Map<String,String> getCEBSerialNosbySerialNo(List<String> serialNo);


    //MeterReading----------------------------------------
    void saveMeterReading(MeterReading meterReading);
    List<MeterReading> saveMeterReadingList(List<MeterReading> meterReadingsList);

    BillCycle setBillCycle_relation(Long billCycleNo);
    Meter setMeter_relation(String serialNo);
    Measure setMeasure_relation(Long measureId);

    Boolean checkPreviousReadingLogs( Long billCycleNo, String license, String province);
    List<MeasureCell> getMeasureCellById(String modelId);
    List<MeterReading> getPreviousReading(String serialNo, Long billCycleNo);
    BigDecimal matchPreviousReading(Long measureId, List<MeterReading> readings);
    

    //MeterReadingLog----------------------------------------
    MeterReadingLog saveMeterReadingLog(MeterReadingLog meterReadingLog);

    Province setProvince_relation(String provinceCode);
    DistributionLicense setLicense_relation(String licenseCode);

    //MeterReadingLogErr--------------------------------------
    void saveMeterReadingLogError(MeterReadingErrLog meterReadingLogError);
    void saveMeterReadingLogErrorList(List<MeterReadingErrLog> meterReadingLogErrorList);

    MeterReadingLog setMeterReadingLog_relation(Long readingId);

    MeterReadingErrLog checkBeforeReProcess(String SerialNo, Long billCycleNo);
    void updateMeterReadingErrLog(MeterReadingErrLog meterReadingErLog);
    void updateMeterReadingLog_FileRead(Long logId);

    //Total Energy--------------------------------------
    List<EnergySummary> CalculateTotalEnergy(List<MeterReading> meterReadingsList,Long BillCycleNo,
                                             String licenseCode, String provinceCode);
    public void saveTotalEnergy(List<EnergySummary> totalEn);

    public Long getCurrentBillCycleNo();


}
