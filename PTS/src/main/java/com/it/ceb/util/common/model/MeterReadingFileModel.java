package com.it.ceb.util.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeterReadingFileModel {

    private String serialNo;

    private String cebSerialNo;

    private String area;

    private String pss;

    private List<MeterProcessRecordModel> meterProcessRecordModelList;
}
