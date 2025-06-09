package com.medai.medaisystem.controller;

import com.medai.medaisystem.dto.PatientResponseDto;
import com.medai.medaisystem.dto.PatientWithReportsDto;
import com.medai.medaisystem.dto.ReportDto;
import com.medai.medaisystem.model.Report;
import com.medai.medaisystem.service.PatientService;
import com.medai.medaisystem.service.ReportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody ReportDto reportDto) {
        try {
            Report savedReport = patientService.kaydetRapor(reportDto);

            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Rapor başarıyla kaydedildi",
                    "reportId", savedReport.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Rapor kaydetme hatası: " + e.getMessage()
            ));
        }
    }

    // Doktora ait tüm raporlar
    @GetMapping("/doktor/{doktorId}")
    public List<Report> doktorRaporlari(@PathVariable Long doktorId) {
        return reportService.doktorRaporlari(doktorId);
    }

    // Tek bir rapor getir
    @GetMapping("/{id}")
    public Optional<Report> raporGetir(@PathVariable Long id) {
        return reportService.raporGetir(id);
    }

    // PDF olarak indir
    @GetMapping("/indir/{id}")
    public void raporPdfIndir(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Report> optionalReport = reportService.raporGetir(id);
        if (optionalReport.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rapor bulunamadı");
            return;
        }

        Report report = optionalReport.get();
        String content = "Doktor: " + report.getDoctor().getAd() + " " + report.getDoctor().getSoyad() + "\n"
                + "Hasta: " + report.getPatient().getAd() + " " + report.getPatient().getSoyad() + "\n"
                + "Tarih: " + report.getTarih() + "\n\n"
                + "Yapay Zeka Sonucu:\n" + report.getYzSonucu();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=rapor_" + report.getId() + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document pdfDoc = new Document();
            PdfWriter.getInstance(pdfDoc, out);
            pdfDoc.open();
            pdfDoc.add(new Paragraph(content));
            pdfDoc.close();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PDF oluşturulamadı: " + e.getMessage());
        }
    }

    // Doktorun hastaları DTO olarak (frontend için)
    @GetMapping("/doctors/{doktorId}/patients")
    public List<PatientResponseDto> doktorunHastalariDto(@PathVariable Long doktorId) {
        return patientService.doktorunHastalariDto(doktorId);
    }

    // Doktorun hastaları ve her hastanın rapor özetleri
    @GetMapping("/doctors/{doktorId}/patients-reports")
    public List<PatientWithReportsDto> doktorunHastalariVeRaporlari(@PathVariable Long doktorId) {
        return patientService.doktorunHastalariVeRaporlari(doktorId);
    }

}
