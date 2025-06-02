package com.it.ceb.pts.repo;

import com.it.ceb.pts.domain.FileUploadHeader;

import java.util.List;

public interface FileUploadHeaderDao {
    void save(FileUploadHeader header) throws Exception;

    void update(FileUploadHeader header) throws Exception;

    FileUploadHeader findById(Long uploadId);

    boolean exists(Long uploadId);

    List<FileUploadHeader> findByBillCycleAndLicenseeAndProvince(
            Long billCycle, String licensee, String province);

    FileUploadHeader findByCompositeKeyAndFileName(
            Long billCycle, String licensee, String province, String fileName);

    Long countUploadedFiles(Long billCycle, String licensee, String province);

    void delete(Long uploadId) throws Exception;

    List<FileUploadHeader> findByBillCycle(Long billCycle);

    public long countFilesByBillCycleAndProvinceAndLicense(Long billCycle, String licenseCode, String provinceCode);

   // public Long generateNextUploadId() throws Exception;
}
