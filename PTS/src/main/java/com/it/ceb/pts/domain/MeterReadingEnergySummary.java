package com.it.ceb.pts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "METER_READING_ENERGY_SUMMARY")
public class MeterReadingEnergySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenMREnergySummary")
	@SequenceGenerator(name = "SeqGenMREnergySummary", sequenceName = "MR_ENERGY_SUMMARY_SEQ", allocationSize = 1)
	@Column(name = "SUMMARY_ID", nullable = false)
    private Long summaryId;

    @Column(name = "BILL_CYCLE")
    private Long billCycle;

    @Size(max = 20)
    @Column(name = "SERIAL_NO", length = 20)
    private String serialNo;

    @Column(name = "CEB_SERIAL_NO", length = 20)
    private String cebSerialNo;

    @Column(name = "IMPORT_ENERGY")
    private BigDecimal importEnergy = BigDecimal.ZERO;

    @Column(name = "EXPORT_ENERGY")
    private BigDecimal exportEnergy = BigDecimal.ZERO;

    @Column(name = "COINCIDENT_PEAK")
    private BigDecimal coincidentPeak = BigDecimal.ZERO;

}