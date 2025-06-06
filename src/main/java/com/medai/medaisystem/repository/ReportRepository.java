package com.medai.medaisystem.repository;

import com.medai.medaisystem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByDoctorId(Long doctorId);
}
