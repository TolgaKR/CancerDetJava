package com.medai.medaisystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String ad;
    private String soyad;
    private LocalDate dogumTarihi;
    private int reportCount;
}
