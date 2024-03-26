package com.ptit.DTO.response;

import com.ptit.model.Course;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LoginResponse {
    private String username;
    private String result;
    private String name;
    private Long id;
    private Date dob;
    private String phone_number;
    private String email;
    private String country;
    private List<Course> course;

    public LoginResponse() {
    }

    public LoginResponse(String username, String result) {
        this.username = username;
        this.result = result;
    }


}
