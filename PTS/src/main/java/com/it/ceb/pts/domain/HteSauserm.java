package com.it.ceb.pts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "HTE_SAUSERM")
public class HteSauserm {
    @Id
    @Column(name = "RN_", nullable = false)
    private Integer id;

    @Column(name = "ACESS_AUTH")
    private Short acessAuth;

    @Column(name = "CHANGE_DT")
    private LocalDate changeDt;

    @Column(name = "CHANGE_FRQ")
    private Short changeFrq;

    @Column(name = "CONF_DT")
    private LocalDate confDt;

    @Column(name = "ENT_DT")
    private LocalDate entDt;

    @Column(name = "EXPIRY_DT")
    private LocalDate expiryDt;

    @Size(max = 1)
    @Column(name = "EXP_STATUS", length = 1)
    private String expStatus;

    @Column(name = "LOGIN_PRD")
    private Boolean loginPrd;

    @Column(name = "LOG_ID")
    private Integer logId;

    @Size(max = 1)
    @Column(name = "LOG_STATUS", length = 1)
    private String logStatus;

    @Column(name = "MODI_DT")
    private LocalDate modiDt;

    @Column(name = "TRXN_AUTH")
    private Boolean trxnAuth;

    @Column(name = "VALID_PRD")
    private Short validPrd;

    @Column(name = "WARN_PRD")
    private Short warnPrd;

    @Column(name = "STATUS")
    private Boolean status;

    @Size(max = 10)
    @Column(name = "USER_LEVEL", length = 10)
    private String userLevel;

    @Size(max = 12)
    @Column(name = "CONF_BY", length = 12)
    private String confBy;

    @Size(max = 12)
    @Column(name = "ENT_BY", length = 12)
    private String entBy;

    @Size(max = 12)
    @Column(name = "MODI_BY", length = 12)
    private String modiBy;

    @Size(max = 12)
    @Column(name = "RPT_USER", length = 12)
    private String rptUser;

    @Size(max = 12)
    @Column(name = "USER_ID", length = 12)
    private String userId;

    @Size(max = 60)
    @Column(name = "JOB_TITLE", length = 60)
    private String jobTitle;

    @Size(max = 60)
    @Column(name = "PSWRD_HINT", length = 60)
    private String pswrdHint;

    @Size(max = 60)
    @Column(name = "USER_NM", length = 60)
    private String userNm;

    @Size(max = 80)
    @Column(name = "OLD_PSWRD", length = 80)
    private String oldPswrd;

    @Size(max = 80)
    @Column(name = "PSWRD", length = 80)
    private String pswrd;

}