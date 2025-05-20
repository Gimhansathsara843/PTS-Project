package com.it.ceb.util.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MeterProcessFileModel {

    private String serialNo;

    private String cebSerialNo;

    private String pss;

    private String status;

    private String fileName;

    private List<MeterProcessRecordModel> meterProcessRecordModelList;
}
