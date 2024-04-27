package com.ptit.service;

import com.ptit.DTO.response.StudentResponse;

public interface StudentService {
    StudentResponse ListStudent(Long id);

    StudentResponse ListStudentByCondition(Long id, Long condition);

    StudentResponse ListStudentByKey(Long id, Long condition, String keyword);
}
