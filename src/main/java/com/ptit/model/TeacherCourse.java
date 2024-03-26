package com.ptit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TeacherCourse {
    @Id
    private Long id;

    @Column
    private Long teacher_id;

    @Column
    private Long course_id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
