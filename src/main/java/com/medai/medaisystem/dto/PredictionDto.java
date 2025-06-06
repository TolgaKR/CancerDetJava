package com.medai.medaisystem.dto;

import java.util.List;

public class PredictionDto {

    private String class_name;
    private double confidence;

    private List<Integer> box;



    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public List<Integer> getBox() {
        return box;
    }

    public void setBox(List<Integer> box) {
        this.box = box;
    }
}