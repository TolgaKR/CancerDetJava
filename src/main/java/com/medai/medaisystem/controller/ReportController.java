package com.medai.medaisystem.controller;

import com.medai.medaisystem.model.Report;
import com.medai.medaisystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/yukle")
    public Report raporKaydet(@RequestBody Report report) {
        return reportService.raporKaydet(report);
    }

    @GetMapping("/doktor/{doktorId}")
    public List<Report> doktorRaporlari(@PathVariable Long doktorId) {
        return reportService.doktorRaporlari(doktorId);
    }

    @GetMapping("/{id}")
    public Optional<Report> raporGetir(@PathVariable Long id) {
        return reportService.raporGetir(id);
    }
}
