package com.medai.medaisystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {
    private Long doctorId;
    private Long patientId;
    private String fotografPath;
    private String yzSonucu;
}
