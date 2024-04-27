package com.ptit.service;

import com.ptit.DTO.response.LoginResponse;

public interface TeacherService {
    LoginResponse teacherLogin(String username, String password);
}
