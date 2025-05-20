package com.it.ceb.pts.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BILL_CYCLE")
public class BillCycle {
    @Id
    @Column(name = "BILL_CYCLE_NO", nullable = false)
    private Long billCycleNo;

    @Column(name = "BILL_YEAR")
    private BigDecimal billYear;

    @Column(name = "BILL_MONTH")
    private BigDecimal billMonth;

    @Size(max = 20)
    @Column(name = "REMARKS", length = 20)
    private String remarks;

    @Column(name = "IS_UPLOAD_LOCKED")
    private Long isUploadLocked;

    @Size(max = 15)
    @Column(name = "CREATED_BY", length = 15)
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATED_BY")
    private LocalDate updatedBy;

    @Size(max = 20)
    @Column(name = "UPDATED_DATE", length = 20)
    private String updatedDate;

    @Column(name = "IS_CURRENT")
    private BigDecimal isCurrent;

    @OneToMany(mappedBy = "billCycle")
    private List<MeterReading> meterReadings;

    @OneToMany(mappedBy = "billCycle")
    private List<MeterReadingLog> meterReadingLogs;

}