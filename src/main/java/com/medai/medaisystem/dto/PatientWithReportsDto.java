package com.medai.medaisystem.dto;

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
public class PatientWithReportsDto {
    private Long id;
    private String ad;
    private String soyad;
    private LocalDate dogumTarihi;
    private List<ReportSummaryDto> raporlar;
}
