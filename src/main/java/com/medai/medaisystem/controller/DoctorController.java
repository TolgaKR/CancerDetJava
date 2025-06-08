package com.medai.medaisystem.controller;

import com.medai.medaisystem.dto.LoginRequestDto;
import com.medai.medaisystem.dto.LoginResponseDto;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        Optional<LoginResponseDto> responseDto = doctorService.login(loginRequestDto);

        if (responseDto.isPresent()) {
            session.setAttribute("doctorEmail", responseDto.get().getEmail()); // email session'a yaz
            return ResponseEntity.ok(responseDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Giriş başarısız");
        }
    }



    @GetMapping("/{id}")
    public Optional<Doctor> doktorGetir(@PathVariable Long id) {
        return doctorService.doktorGetir(id);
    }
}
