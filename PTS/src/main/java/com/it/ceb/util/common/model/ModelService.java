package com.it.ceb.util.common.model;

import com.it.ceb.pts.domain.MeterReading;
import com.it.ceb.pts.domain.Province;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelService {
    private MeterReadingModel convertModel(MeterReading meterReading) {
        MeterReadingModel model = new MeterReadingModel();
        model.setId(meterReading.getReadingId());
        //model.setBillCycleNo(meterReading.getBillCycle().getBillCycleNo());
        //model.setMeasureId(meterReading.getMeasure().getMeasureId());
        model.setCurrentReading(meterReading.getCurrentReading());
        model.setCreatedBy(meterReading.getCreatedBy());
        model.setCreatedDate(meterReading.getCreatedDate());
        model.setUpdatedBy(meterReading.getUpdatedBy());
        model.setUpdatedDate(meterReading.getUpdatedDate());
        model.setReadingMethod(meterReading.getReadingMethod());
        model.setPreviousReading(meterReading.getPreviousReading());
        model.setEnergy(meterReading.getEnergy());
        model.setSerialNo(meterReading.getSerialNo());
        //model.setCebSerialNo(meterReading.getMeter().getCebSerialNo()); // Set Meter serial number
        return model;
    }

    private ProvinceModel convertModel(Province province) {
        ProvinceModel model = new ProvinceModel();
        model.setProvinceCode(province.getProvinceCode());
        model.setProvinceName(province.getProvinceName());
        model.setLicenseCode(province.getLicenseCode());
        return model;
    }

    private EnergySummaryModel convertModel(com.it.ceb.pts.domain.EnergySummary total) {
        EnergySummaryModel model = new EnergySummaryModel();
        model.setDayOffPeak(total.getDayOffPeak() == 'D' ? "Day" : total.getDayOffPeak() == 'O' ? "Off peak" : total.getDayOffPeak() == 'P' ? "Peak" : "");
        model.setTotalEnergy(total.getTotalEnergy());
        return model;
    }

    public List<MeterReadingModel> getAllMeterReadings(List<MeterReading> meterReadings) {
        return meterReadings.stream().map(this::convertModel).collect(Collectors.toList());
    }

    public List<ProvinceModel> getAllProvinces(List<Province> provinces) {
        return provinces.stream().map(this::convertModel).collect(Collectors.toList());
    }

    public List<EnergySummaryModel> getAllTotals(List<com.it.ceb.pts.domain.EnergySummary> totals) {
        return totals.stream().map(this::convertModel).collect(Collectors.toList());
    }

    public List<MeterReadingFileModel> convertToMeterReadingFilemodel(List<MeterReadingResultModel> meterReadings) {
        // Group by SerialNo, Area, and PSS Name using a Map
        Map<String, List<MeterReadingResultModel>> groupedData = meterReadings.stream()
                .collect(Collectors.groupingBy(reading ->
                        reading.getSerialNo() + "|" + reading.getArea() + "|" + reading.getPssName()));

        // Convert the grouped map to MeterReadingFileModel list
        return groupedData.entrySet().stream()
                .map(entry -> {
                    String[] keys = entry.getKey().split("\\|");
                    MeterReadingFileModel fileModel = new MeterReadingFileModel();
                    fileModel.setSerialNo(keys[0]);
                    fileModel.setArea(keys[1]);
                    fileModel.setPss(keys[2]);

                    // Convert each MeterReadingResultModel to MeterProcessRecordModel
                    List<MeterProcessRecordModel> recordModels = entry.getValue().stream()
                            .map(reading -> {
                                MeterProcessRecordModel recordModel = new MeterProcessRecordModel();
                                recordModel.setReadingId(reading.getReadingId());
                                recordModel.setCurrentReading(reading.getCurrentReading());
                                recordModel.setPreviousReading(reading.getPreviousReading());
                                recordModel.setEnergy(reading.getEnergy());
                                recordModel.setMeasureName(reading.getMeasureType());
                                return recordModel;
                            })
                            .collect(Collectors.toList());

                    fileModel.setMeterProcessRecordModelList(recordModels);
                    return fileModel;
                })
                .collect(Collectors.toList());
    }
}
