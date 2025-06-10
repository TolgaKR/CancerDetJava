package com.medai.medaisystem.controller; // Bu paketin doğru olduğundan emin ol

import com.medai.medaisystem.dto.PredictionResultDto;
import com.medai.medaisystem.service.MelanomaDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // TÜM ANNOTATION'LARI İMPORT ET
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ImageUploadController {

    private final MelanomaDetectionService melanomaDetectionService;

    @Autowired
    public ImageUploadController(MelanomaDetectionService melanomaDetectionService) {
        this.melanomaDetectionService = melanomaDetectionService;
    }

    @PostMapping("/process-image")
    public ResponseEntity<?> processImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Lütfen yüklenecek bir dosya seçin.", HttpStatus.BAD_REQUEST);
        }

        File tempFile = null;
        try {

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String tempFileName = UUID.randomUUID().toString() + fileExtension;
            Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), tempFileName);
            tempFile = tempFilePath.toFile(); // tempFile'a değer ata
            file.transferTo(tempFile);

            System.out.println("Geçici dosya oluşturuldu: " + tempFile.getAbsolutePath());

            // Servis katmanını çağır
            PredictionResultDto predictionResult = melanomaDetectionService.getMelanomaPrediction(tempFile);

            return new ResponseEntity<>(predictionResult, HttpStatus.OK);

        } catch (IOException e) {
            System.err.println("Dosya işlenirken hata oluştu veya Flask API'ye bağlanılamadı: " + e.getMessage());
            return new ResponseEntity<>("Görsel işlenirken bir hata oluştu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Beklenmedik bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Sunucu tarafında beklenmedik bir hata oluştu.", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            // Geçici dosyayı sil (hata olsa bile silinmesini sağla)
            if (tempFile != null && tempFile.exists()) {
                if (tempFile.delete()) {
                    System.out.println("Geçici dosya silindi: " + tempFile.getAbsolutePath());
                } else {
                    System.err.println("Geçici dosya silinemedi: " + tempFile.getAbsolutePath());
                }
            }
        }
    }


    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller çalışıyor!";
    }
}