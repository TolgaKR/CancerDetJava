package com.medai.medaisystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String soyad;
    private LocalDate dogumTarihi;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctor_id") // veritabanında foreign key için
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();
}
