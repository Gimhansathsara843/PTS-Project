package com.it.ceb.pts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "FILE_UPLOAD_HEADER")
public class FileUploadHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FileUploadSeqGen")
    @SequenceGenerator(name = "FileUploadSeqGen", sequenceName = "FILE_UPLOAD_HEADER_SEQ", allocationSize = 1)
    @Column(name = "UPLOAD_ID", nullable = false)
    private Long uploadId;

    @Size(max = 200)
    @Column(name = "FILE_NAME", length = 200)
    private String fileName;

    @Column(name = "IS_UPLOADED")
    private Long isUploaded;

    @Size(max = 15)
    @Column(name = "UPLOADED_BY", length = 15)
    private String uploadedBy;

    @Column(name = "UPLOADED_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate uploadedDate;

//    @Size(max = 20)
//    @Column(name = "FILE_TYPE", length = 20)
//    private String fileType;

    @Column(name = "BILL_CYCLE_NO", nullable = false)
    private Long billCycleNo;

    @Size(max = 100)
    @Column(name = "LICENSE_CODE", nullable = false, length = 100)
    private String licenseCode;

    @Size(max = 50)
    @Column(name = "PROVINCE_CODE", nullable = false, length = 50)
    private String provinceCode;
}
