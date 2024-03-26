package com.ptit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Course {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Long type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
