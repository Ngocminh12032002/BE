package com.ptit.service;

import com.ptit.DTO.response.ListClassResponse;

public interface SubjectClassService {
    ListClassResponse listClassByCourse(Long courseId, Long teacherId);
}
