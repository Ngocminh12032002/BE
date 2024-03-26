package com.ptit.DTO.request;

import lombok.Data;

@Data
public class InputScoreRequest {
    private double attendance;
    private double test;
    private double assignment;
    private double exam;
    private double gpa;

}
