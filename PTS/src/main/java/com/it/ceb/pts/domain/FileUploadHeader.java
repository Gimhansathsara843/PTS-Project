package com.it.ceb.pts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "FILE_UPLOAD_HEADER")
public class FileUploadHeader {
    @EmbeddedId
    private FileUploadHeaderId id;

    @Size(max = 200)
    @Column(name = "FILE_NAME", length = 200)
    private String fileName;

    @Column(name = "IS_UPLOADED")
    private Long isUploaded;

    @Size(max = 15)
    @Column(name = "UPLOADED_BY", length = 15)
    private String uploadedBy;

    @Column(name = "UPLOADED_DATE")
    private LocalDate uploadedDate;

    @Size(max = 20)
    @Column(name = "FILE_TYPE", length = 20)
    private String fileType;

}