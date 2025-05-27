package com.it.ceb.pts.repo;

import com.it.ceb.pts.domain.FileUploadHeader;
import com.it.ceb.pts.domain.FileUploadHeaderId;

import java.util.List;

public interface FileUploadHeaderDao {
    void save(FileUploadHeader header) throws Exception;

    void update(FileUploadHeader header);

    FileUploadHeader findById(FileUploadHeaderId id);

    boolean exists(FileUploadHeaderId id);

    List<FileUploadHeader> findByBillCycleAndLicenseeAndProvince(
            Long billCycle, String licensee, String province);

    FileUploadHeader findByCompositeKeyAndFileName(
            Long billCycle, String licensee, String province, String fileName);

    Long countUploadedFiles(Long billCycle, String licensee, String province);

    void delete(FileUploadHeaderId id);

    List<FileUploadHeader> findByBillCycle(Long billCycle);
}
