package com.medai.medaisystem.service;

import com.medai.medaisystem.model.Report;
import com.medai.medaisystem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report raporKaydet(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> doktorRaporlari(Long doktorId) {
        return reportRepository.findByDoctorId(doktorId);
    }

    public Optional<Report> raporGetir(Long id) {
        return reportRepository.findById(id);
    }
}
