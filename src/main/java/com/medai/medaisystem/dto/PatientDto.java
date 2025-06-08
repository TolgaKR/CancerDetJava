package com.medai.medaisystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PatientDto {
    private String ad;
    private String soyad;
    private LocalDate dogumTarihi;
    private Long doctorId;
}
