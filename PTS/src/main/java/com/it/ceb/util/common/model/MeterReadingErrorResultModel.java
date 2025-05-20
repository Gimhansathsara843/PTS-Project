package com.it.ceb.util.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingErrorResultModel {

    private String serialNo;

    private String cebSerialNo;

    private Long errLogId;

    private String status;

    private String reason;

    private Long attempts;
}
