package com.ptit.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double attendance;

    @Column
    private Double test;

    @Column
    private Double assignment;

    @Column
    private Double exam;

    @Column
    private Double gpa;

    @Column
    private int student_id;


}
