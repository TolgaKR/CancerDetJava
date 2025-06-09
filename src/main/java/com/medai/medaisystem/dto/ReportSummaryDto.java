package com.medai.medaisystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReportSummaryDto {
    private Long id;
    private String tarih;
    private String yzSonucu; // veya başlık gibi bir alan eklenebilir
}
