package com.it.ceb.util.common.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MeterReadingModel {
    private Long id;
    private Long billCycleNo;
    private Long measureId;
    private BigDecimal currentReading;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String readingMethod;
    private BigDecimal previousReading;
    private BigDecimal energy;
    private String serialNo;
    private String cebSerialNo;
}

