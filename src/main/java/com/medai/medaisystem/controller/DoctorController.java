package com.medai.medaisystem.controller;

import com.medai.medaisystem.dto.LoginRequestDto;
import com.medai.medaisystem.dto.LoginResponseDto;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @PostMapping("/kayit")
    public Doctor doktorKaydet(@RequestBody Doctor doctor) {
        return doctorService.kaydet(doctor);
    }


    @PostMapping("/login")
    public Optional<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return doctorService.login(loginRequestDto);
    }


    @GetMapping("/{id}")
    public Optional<Doctor> doktorGetir(@PathVariable Long id) {
        return doctorService.doktorGetir(id);
    }
}
