package com.ptit.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subject_class")
public class SubjectClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long subject_id;

    @Column
    private Long teacher_id;

    @Column
    private Double factor_assignment;

    @Column
    private Double factor_attendance;

    @Column
    private Double factor_test;

    @Column
    private Double factor_exam;

    @Column
    private Long course_id;
}
