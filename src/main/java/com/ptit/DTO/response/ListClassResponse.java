package com.ptit.DTO.response;

import com.ptit.model.SubjectClass;
import lombok.Data;

import java.util.List;

@Data
public class ListClassResponse {
    private List<SubjectClassInfo> subjectClassInfos;
}

