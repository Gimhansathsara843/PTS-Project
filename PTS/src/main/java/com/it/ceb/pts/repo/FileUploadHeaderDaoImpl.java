package com.it.ceb.pts.repo;

import com.it.ceb.pts.domain.FileUploadHeaderId;
import com.it.ceb.pts.repo.FileUploadHeaderDao;
import com.it.ceb.pts.domain.FileUploadHeader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FileUploadHeaderDaoImpl implements FileUploadHeaderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(FileUploadHeader header) throws Exception {
   //     try {
            entityManager.persist(header);
            entityManager.flush();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save FileUploadHeader: " + e.getMessage(), e);
//        }
    }

    @Override
    public void update(FileUploadHeader header) {
        try {
            entityManager.merge(header);
            entityManager.flush();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update FileUploadHeader: " + e.getMessage(), e);
        }
    }

    @Override
    public FileUploadHeader findById(FileUploadHeaderId id) {
        try {
            return entityManager.find(FileUploadHeader.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find FileUploadHeader by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(FileUploadHeaderId id) {
        try {
            FileUploadHeader header = entityManager.find(FileUploadHeader.class, id);
            return header != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<FileUploadHeader> findByBillCycleAndLicenseeAndProvince(
            Long billCycle, String licensee, String province) {
        try {
            String jpql = "SELECT f FROM FileUploadHeader f WHERE " +
                    "f.id.billCycleNo = :billCycle AND " +
                    "f.id.licensee = :licensee AND " +
                    "f.id.province = :province";

            TypedQuery<FileUploadHeader> query = entityManager.createQuery(jpql, FileUploadHeader.class);
            query.setParameter("billCycle", billCycle);
            query.setParameter("licensee", licensee);
            query.setParameter("province", province);

            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find FileUploadHeaders: " + e.getMessage(), e);
        }
    }

    @Override
    public FileUploadHeader findByCompositeKeyAndFileName(
            Long billCycle, String licensee, String province, String fileName) {
        try {
            String jpql = "SELECT f FROM FileUploadHeader f WHERE " +
                    "f.id.billCycleNo = :billCycle AND " +
                    "f.id.licensee = :licensee AND " +
                    "f.id.province = :province AND " +
                    "f.fileName = :fileName";

            TypedQuery<FileUploadHeader> query = entityManager.createQuery(jpql, FileUploadHeader.class);
            query.setParameter("billCycle", billCycle);
            query.setParameter("licensee", licensee);
            query.setParameter("province", province);
            query.setParameter("fileName", fileName);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find FileUploadHeader by filename: " + e.getMessage(), e);
        }
    }

    @Override
    public Long countUploadedFiles(Long billCycle, String licensee, String province) {
        try {
            String jpql = "SELECT COUNT(f) FROM FileUploadHeader f WHERE " +
                    "f.id.billCycleNo = :billCycle AND " +
                    "f.id.licensee = :licensee AND " +
                    "f.id.province = :province";

            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("billCycle", billCycle);
            query.setParameter("licensee", licensee);
            query.setParameter("province", province);

            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to count FileUploadHeaders: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(FileUploadHeaderId id) {
        try {
            FileUploadHeader header = entityManager.find(FileUploadHeader.class, id);
            if (header != null) {
                entityManager.remove(header);
                entityManager.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete FileUploadHeader: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FileUploadHeader> findByBillCycle(Long billCycle) {
        try {
            String jpql = "SELECT f FROM FileUploadHeader f WHERE f.id.billCycleNo = :billCycle";
            TypedQuery<FileUploadHeader> query = entityManager.createQuery(jpql, FileUploadHeader.class);
            query.setParameter("billCycle", billCycle);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find FileUploadHeaders by bill cycle: " + e.getMessage(), e);
        }
    }
}

