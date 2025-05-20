package com.it.ceb.util.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingRecordModel {

    private Long readingId;

    private String measure;

    private BigDecimal currentReading;

    private BigDecimal previousReading;

    private BigDecimal energy;

    private Long pointId;
}

