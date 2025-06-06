package com.medai.medaisystem.dto;

import java.util.List;

public class PredictionResultDto {
    private String status;
    private String message;
    private List<PredictionDto> predictions;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PredictionDto> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDto> predictions) {
        this.predictions = predictions;
    }
}