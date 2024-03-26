package com.ptit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Date dob;

    @Column
    private String phone_number;

    @Column
    private String email;

    @Column
    private String country;

}
