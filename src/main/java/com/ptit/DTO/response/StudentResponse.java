package com.ptit.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class StudentResponse {
    List<StudentMark> studentMarkList;
}

