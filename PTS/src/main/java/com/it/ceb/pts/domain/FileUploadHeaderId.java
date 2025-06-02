//package com.it.ceb.pts.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.Hibernate;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Getter
//@Setter
//@Embeddable
//public class FileUploadHeaderId implements Serializable {
//    private static final long serialVersionUID = 5138741678373660936L;
//    @NotNull
//    @Column(name = "BILL_CYCLE_NO", nullable = false)
//    private Long billCycleNo;
//
//    @Size(max = 100)
//    @NotNull
//    @Column(name = "LICENSEE", nullable = false, length = 100)
//    private String licensee;
//
//    @Size(max = 50)
//    @NotNull
//    @Column(name = "PROVINCE", nullable = false, length = 50)
//    private String province;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        FileUploadHeaderId entity = (FileUploadHeaderId) o;
//        return Objects.equals(this.licensee, entity.licensee) &&
//                Objects.equals(this.province, entity.province) &&
//                Objects.equals(this.billCycleNo, entity.billCycleNo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(licensee, province, billCycleNo);
//    }
//
//}