package com.medai.medaisystem.service;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import okio.BufferedSource;
import okio.Okio;

import com.medai.medaisystem.dto.PredictionResultDto;
import org.springframework.stereotype.Service;

@Service
public class MelanomaDetectionService {

    private static final String FLASK_API_URL = "http://localhost:5000/predict";

    private final OkHttpClient httpClient;
    private final Gson gson;

    public MelanomaDetectionService() {
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public PredictionResultDto getMelanomaPrediction(File imageFile) throws IOException {
        if (imageFile == null || !imageFile.exists() || !imageFile.isFile()) {
            throw new IllegalArgumentException("Geçerli bir görsel dosyası sağlanmalı.");
        }

        MediaType mediaType = MediaType.parse("image/*");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        imageFile.getName(),

                        RequestBody.create(imageFile, mediaType)
                )
                .build();

        Request request = new Request.Builder()
                .url(FLASK_API_URL)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorMessage = response.body() != null ? response.body().string() : "Bilinmeyen hata";
                System.err.println("Flask API hatası: " + response.code() + ", Mesaj: " + errorMessage);
                throw new IOException("Flask API başarısız yanıt: HTTP " + response.code() + " - " + errorMessage);
            }

            String jsonResponse = response.body().string();
            System.out.println("Flask API yanıtı: " + jsonResponse);

            return gson.fromJson(jsonResponse, PredictionResultDto.class);

        } catch (IOException e) {
            System.err.println("Flask API bağlantı hatası: " + e.getMessage());
            throw e;
        }
    }
}