package com.it.ceb.util.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MeterPointModel {
    private Long id;
    private String serialNo;
    private String cebSerialNo;
    private String area;
    private String pss;
    private String status;
    private List<MeterReadingRecordModel> meterReadingModels;

    public MeterPointModel(Long id, String serialNo, String cebSerialNo, String area,String pss) {
        this.id = id;
        this.serialNo = serialNo;
        this.cebSerialNo = cebSerialNo;
        this.area = area;
        this.pss = pss;
        this.status = "Incomplete";
        this.meterReadingModels = new ArrayList<>();
    }

}