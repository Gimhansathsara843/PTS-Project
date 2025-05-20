package com.it.ceb.util.common.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MeterProcessRecordModel {
    private Long readingId;

    private String measureName;

    private BigDecimal currentReading;

    private BigDecimal previousReading;

    private BigDecimal energy;

}
