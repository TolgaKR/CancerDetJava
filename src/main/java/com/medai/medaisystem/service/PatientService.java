package com.medai.medaisystem.service;

import com.medai.medaisystem.dto.*;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.model.Patient;
import com.medai.medaisystem.model.Report;
import com.medai.medaisystem.repository.DoctorRepository;
import com.medai.medaisystem.repository.PatientRepository;
import com.medai.medaisystem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ReportRepository reportRepository;

    // DTO ile hasta kaydetme
    public Patient hastaKaydetDto(PatientDto dto) {
        Patient patient = new Patient();
        patient.setAd(dto.getAd());
        patient.setSoyad(dto.getSoyad());
        patient.setDogumTarihi(dto.getDogumTarihi());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        patient.setDoctor(doctor);

        return patientRepository.save(patient);
    }
    public List<PatientResponseDto> doktorunHastalariDto(Long doctorId) {
        List<Patient> hastalar = patientRepository.findByDoctorId(doctorId);

        return hastalar.stream().map(hasta -> {
            PatientResponseDto dto = new PatientResponseDto();
            dto.setId(hasta.getId());
            dto.setAd(hasta.getAd());
            dto.setSoyad(hasta.getSoyad());
            dto.setDogumTarihi(hasta.getDogumTarihi());
            dto.setReportCount(hasta.getReports() != null ? hasta.getReports().size() : 0);
            return dto;
        }).toList();
    }


    public Optional<Patient> hastaGetir(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> doktorunHastalari(Long doctorId) {
        return patientRepository.findByDoctorId(doctorId);
    }

    public Report kaydetRapor(ReportDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));

        Report report = new Report();
        report.setDoctor(doctor);
        report.setPatient(patient);
        report.setFotografPath(dto.getFotografPath());
        report.setYzSonucu(dto.getYzSonucu());
        report.setTarih(LocalDateTime.now());

        return reportRepository.save(report);
    }

    public List<Report> doktorunRaporlari(Long doctorId) {
        return reportRepository.findByDoctorId(doctorId);
    }



    public List<PatientWithReportsDto> doktorunHastalariVeRaporlari(Long doctorId) {
        List<Patient> hastalar = patientRepository.findByDoctorId(doctorId);

        return hastalar.stream().map(hasta -> {
            PatientWithReportsDto dto = new PatientWithReportsDto();
            dto.setId(hasta.getId());
            dto.setAd(hasta.getAd());
            dto.setSoyad(hasta.getSoyad());
            dto.setDogumTarihi(hasta.getDogumTarihi());

            List<ReportSummaryDto> raporlar = hasta.getReports().stream().map(rapor -> {
                ReportSummaryDto raporDto = new ReportSummaryDto();
                raporDto.setId(rapor.getId());
                raporDto.setTarih(rapor.getTarih().toString());
                raporDto.setYzSonucu(rapor.getYzSonucu()); // ya da sadece başlık
                return raporDto;
            }).toList();

            dto.setRaporlar(raporlar);
            return dto;
        }).toList();
    }




}
