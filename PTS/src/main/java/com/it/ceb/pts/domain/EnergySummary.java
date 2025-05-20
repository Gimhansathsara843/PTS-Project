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
@Table(name = "ENERGY_SUMMARY")
public class EnergySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenEnergySummary")
	@SequenceGenerator(name = "SeqGenEnergySummary", sequenceName = "ENERGY_SUMMARY_SEQ", allocationSize = 1)
	@Column(name = "SUMMARY_ID", nullable = false)
    private Long summaryId;

    @Column(name = "TOTAL_ENERGY")
    private BigDecimal totalEnergy;

    @Column(name = "DAY_OFF_PEAK")
    private Character dayOffPeak;

    @Column(name = "BILL_CYCLE_NO")
    private Long billCycleNo;

    @Size(max = 10)
    @Column(name = "PROVINCE_CODE", length = 10)
    private String provinceCode;

    @Size(max = 20)
    @Column(name = "LICENSE_CODE", length = 20)
    private String licenseCode;

    public EnergySummary(Character dop, Long billCycleNo, String licenseCode, String provinceCode) {
        this.dayOffPeak=dop;
        this.totalEnergy=new BigDecimal(0);
        this.billCycleNo=billCycleNo;
        this.licenseCode=licenseCode;
        this.provinceCode=provinceCode;
    }

    public EnergySummary() {
    }
}