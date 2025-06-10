package com.medai.medaisystem.service;

import com.medai.medaisystem.dto.LoginRequestDto;
import com.medai.medaisystem.dto.LoginResponseDto;
import com.medai.medaisystem.model.Doctor;
import com.medai.medaisystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    public Doctor kaydet(Doctor doktor) {
        return doctorRepository.save(doktor);
    }


    public Optional<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        Optional<Doctor> doktorOpt = doctorRepository.findByEmail(loginRequestDto.getEmail());

        if (doktorOpt.isPresent()) {
            Doctor doktor = doktorOpt.get();
            if (doktor.getSifre().equals(loginRequestDto.getSifre())) {
                // Şifre doğruysa sadece gerekli bilgileri DTO'ya koyup döndür
                LoginResponseDto responseDto = new LoginResponseDto(
                        doktor.getId(),
                        doktor.getAd(),
                        doktor.getSoyad(),
                        doktor.getEmail()
                );
                return Optional.of(responseDto);
            }
        }

        // Giriş başarısız
        return Optional.empty();
    }

    public Optional<Doctor> doktorGetir(Long id) {
        return doctorRepository.findById(id);
    }






}
