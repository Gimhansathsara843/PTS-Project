package com.it.ceb.pts.repo;

import com.it.ceb.pts.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class MeterProcessDaoImpl implements MeterProcessDao {

    @PersistenceContext
    private EntityManager entityManager;

    //-----------------------------------------------------------------------------------
    //                                        General
    //-----------------------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<String> getEnergyPrefixes() {
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT m.energyPrefix FROM MeterModel m",
                    String.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("error while getting the meter by serial no1");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Meter getMeterBySerialNo(String serialNo) {
        try {
            TypedQuery<Meter> query = entityManager.createQuery(
                    "SELECT m FROM Meter m WHERE m.serialNo= :serialNo",
                    Meter.class
            );
            query.setParameter("serialNo", serialNo);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("error while getting the meter by serial no2");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MeterPoint getMeterPointBySerialNo(String serialNo) {
        try {
            TypedQuery<MeterPoint> query = entityManager.createQuery(
                    "SELECT m FROM MeterPoint m WHERE m.meter.serialNo= :serialNo",
                    MeterPoint.class
            );
            query.setParameter("serialNo", serialNo);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("error while getting the meter by serial no3");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Map<String,String> getCEBSerialNosbySerialNo(List<String> serialNos){
        Map<String,String> CEBSerialNos = new HashMap<>();

        for (String serialNo : serialNos) {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT m.cebSerialNo FROM Meter m WHERE m.serialNo = :serialNo",
                    String.class
            );
            query.setParameter("serialNo", serialNo);
            CEBSerialNos.put(serialNo, query.getSingleResult());
        }
        return CEBSerialNos;
    }


    //-----------------------------------------------------------------------------------
    //                   Meter Reading
    //-----------------------------------------------------------------------------------

    @Override
    @Transactional
    public void saveMeterReading(MeterReading meterReading) {
        //meterReading.setReadingId(setReadingId_SEQ());
        entityManager.persist(meterReading);
    }

    @Override
    @Transactional
    public List<MeterReading> saveMeterReadingList(List<MeterReading> meterReadingsList) {
        for (MeterReading meterReading : meterReadingsList) {
            //meterReading.setReadingId(setReadingId_SEQ());
            entityManager.persist(meterReading);
        }
        entityManager.flush();
        return meterReadingsList;
    }

    /*private Long setReadingId_SEQ(){
        BigDecimal nextVal = (BigDecimal) entityManager
                .createNativeQuery("SELECT METER_READING_SEQ.NEXTVAL FROM SYS.DUAL")
                .getSingleResult();
        return nextVal.longValue();
    }*/

    @Override
    public BillCycle setBillCycle_relation(Long billCycleNo){
        return entityManager.find(BillCycle.class, billCycleNo);
    }

    @Override
    public Meter setMeter_relation(String CEBserialNo){
        Meter mt = entityManager.find(Meter.class, CEBserialNo);
        if (mt == null) {
            mt = new Meter();
            mt.setCebSerialNo(CEBserialNo);
            entityManager.persist(mt);
        }
        return mt;
    }

    @Override
    public Measure setMeasure_relation(Long measureId){
        Measure msr = entityManager.find(Measure.class, measureId);
        if (msr == null) {
            msr = new Measure();
            msr.setMeasureId(measureId);
            entityManager.persist(msr);
        }
        return msr;
    }


    //  -------------   helper methods for meter reading table properties ------------------------

    @Override
    public Boolean checkPreviousReadingLogs(Long billCycleNo, String license, String province){
        TypedQuery<MeterReadingLog> query = entityManager.createQuery(
                "SELECT m FROM MeterReadingLog m WHERE m.license.licenseCode= :license AND m.province.provinceCode= :province AND m.billCycle.billCycleNo= :billCycleNo",
                MeterReadingLog.class);
        query.setParameter("license", license);
        query.setParameter("province", province);
        query.setParameter("billCycleNo", billCycleNo);
        if(query.getResultList().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<MeterReading> getPreviousReading(String serialNo, Long billCycleNo) {
        Long prevBillCycleNo = billCycleNo - 1;
        try {
            TypedQuery<MeterReading> query = entityManager.createQuery(
                    "SELECT m FROM MeterReading m WHERE m.serialNo= :serialNo AND m.billCycle.billCycleNo= :billCycleNo",
                    MeterReading.class);
            query.setParameter("serialNo", serialNo);
            query.setParameter("billCycleNo", prevBillCycleNo);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error while getting previous readings:"+e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public BigDecimal matchPreviousReading(Long measureId, List<MeterReading> readings) {
        if (readings == null || readings.isEmpty()) {
            return new BigDecimal("0");
        }

        for (MeterReading reading : readings) {
            if (reading.getMeasure().getMeasureId().equals(measureId)) {
                return reading.getCurrentReading();
            }
        }
        return new BigDecimal("0");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasureCell> getMeasureCellById(String modelId) {
        try {
            TypedQuery<MeasureCell> query = entityManager.createQuery(
                    "SELECT m FROM MeasureCell m WHERE m.modelId= :modelId",
                    MeasureCell.class
            );
            query.setParameter("modelId", Long.parseLong(modelId));
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("error while getting the measure cell by model id");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    //-----------------------------------------------------------------------------------
    //                   Meter Reading Log
    //-----------------------------------------------------------------------------------

    @Transactional
    @Override
    public MeterReadingLog saveMeterReadingLog(MeterReadingLog meterReadingLog) {
        /*BigDecimal nextVal = (BigDecimal) entityManager
                .createNativeQuery("SELECT METER_READING_LOG_SEQ.NEXTVAL FROM SYS.DUAL")
                .getSingleResult();
        meterReadingLog.setReadingLogId(nextVal.longValue());*/
        entityManager.persist(meterReadingLog);
        return meterReadingLog;
    }

    @Override
    public Province setProvince_relation(String provinceCode){
        return entityManager.find(Province.class, provinceCode);
    }

    @Override
    public DistributionLicense setLicense_relation(String licenseCode){
        return  entityManager.find(DistributionLicense.class, licenseCode);
    }
/*
    private Long setReadingErrLogId_SEQ(){
        BigDecimal nextVal = (BigDecimal) entityManager
                .createNativeQuery("SELECT METER_READING_ERR_LOG_SEQ.NEXTVAL FROM SYS.DUAL")
                .getSingleResult();
        return nextVal.longValue();
    }
*/
/*
    @Override
    public BigDecimal getSummeryLogId() {
        BigDecimal nextVal = (BigDecimal) entityManager
                .createNativeQuery("SELECT METER_READING_LOG_SEQ.NEXTVAL FROM SYS.DUAL")
                .getSingleResult();
        return nextVal;
    }*/

    //-----------------------------------------------------------------------------------
    //                   Meter Reading Error Log
    //-----------------------------------------------------------------------------------

    @Transactional
    @Override
    public void saveMeterReadingLogError(MeterReadingErrLog meterReadingLogError) {
        //meterReadingLogError.setErrorLogId(setReadingErrLogId_SEQ());
        entityManager.persist(meterReadingLogError);
    }

    @Transactional
    @Override
    public void saveMeterReadingLogErrorList(List<MeterReadingErrLog> meterReadingLogErrorList){
        for (MeterReadingErrLog meterReadingErrLog : meterReadingLogErrorList) {
           // meterReadingErrLog.setErrorLogId(setReadingErrLogId_SEQ());
            entityManager.persist(meterReadingErrLog);
        }
    }

    @Override
    public MeterReadingLog setMeterReadingLog_relation(Long readingId){
        return entityManager.find(MeterReadingLog.class, readingId);
    }


    // -------------------      Error log files re processing     --------------------------------

    @Override
    public MeterReadingErrLog checkBeforeReProcess(String SerialNo, Long billCycleNo){
        TypedQuery<MeterReadingErrLog> query = entityManager.createQuery(
                "SELECT m FROM MeterReadingErrLog m WHERE m.serialNo= :serialNo AND m.status= :status" +
                        " AND m.meterReadingLog.billCycle.billCycleNo= :billCycleNo",
                MeterReadingErrLog.class);
        query.setParameter("serialNo", SerialNo);
        query.setParameter("status", "UNSOLVED");
        query.setParameter("billCycleNo", billCycleNo);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateMeterReadingLog_FileRead(Long logId) {
        MeterReadingLog meterReadingLog = entityManager.find(MeterReadingLog.class, logId);
        meterReadingLog.setFilesRead(meterReadingLog.getFilesRead()+1);
        if(Objects.equals(meterReadingLog.getFilesRead(), meterReadingLog.getFiles())){
            meterReadingLog.setStatus("OK");
        }
        entityManager.merge(meterReadingLog);
    }

    @Override
    @Transactional
    public void updateMeterReadingErrLog(MeterReadingErrLog meterReadingErrLog) {
        entityManager.merge(meterReadingErrLog);
    }

    //-----------------------------------------------------------------------------------
    //                   Total Energy calculation
    //-----------------------------------------------------------------------------------

    @Override
    public List<EnergySummary> CalculateTotalEnergy(
            List<MeterReading> meterReadingsList,
            Long BillCycleNo,
            String licenseCode,
            String provinceCode) {
        List<EnergySummary> totalEnAr = new ArrayList<>();
        EnergySummary dayTotal = new EnergySummary('D', BillCycleNo, licenseCode, provinceCode);
        EnergySummary peakTotal = new EnergySummary('O', BillCycleNo, licenseCode, provinceCode);
        EnergySummary offTotal = new EnergySummary('P', BillCycleNo, licenseCode, provinceCode);

        for (MeterReading meterReading : meterReadingsList) {
            if ("D".equals(meterReading.getMeasure().getDayPeakOff())) {
                System.out.println("En direct:"+meterReading.getEnergy());
                System.out.println(dayTotal.getTotalEnergy().add(meterReading.getEnergy()));
                dayTotal.setTotalEnergy(dayTotal.getTotalEnergy().add(meterReading.getEnergy()));
            } else if ("P".equals(meterReading.getMeasure().getDayPeakOff())) {
                peakTotal.setTotalEnergy(peakTotal.getTotalEnergy().add(meterReading.getEnergy()));
            } else if ("O".equals(meterReading.getMeasure().getDayPeakOff())) {
                offTotal.setTotalEnergy(offTotal.getTotalEnergy().add(meterReading.getEnergy()));
            }
        }

        totalEnAr.add(dayTotal);
        System.out.println("print en:"+dayTotal.getTotalEnergy());
        totalEnAr.add(peakTotal);
        totalEnAr.add(offTotal);
        return  totalEnAr;
    }

    @Override
    @Transactional
    public void saveTotalEnergy(List<EnergySummary> totalEn) {
        for (EnergySummary total : totalEn) {
            try{
               // BigDecimal nextVal = (BigDecimal) entityManager
               //         .createNativeQuery("SELECT ENERGY_SUMMERY_SEQ.NEXTVAL FROM SYS.DUAL")
                //        .getSingleResult();
                //total.setId(nextVal.longValue());
                entityManager.persist(total);
            }catch (Exception e){
                System.out.println("an error occured :"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


}