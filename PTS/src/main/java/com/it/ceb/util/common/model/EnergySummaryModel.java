package com.it.ceb.util.common.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnergySummaryModel {
    private String dayOffPeak;
    private BigDecimal totalEnergy;
}
