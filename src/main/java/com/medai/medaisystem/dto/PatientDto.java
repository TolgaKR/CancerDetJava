package com.medai.medaisystem.dto;

import com.medai.medaisystem.model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PatientDto {
    private String ad;
    private String soyad;
    private LocalDate dogumTarihi;
    private Long doctorId;
    private List<Report> reports;
}
