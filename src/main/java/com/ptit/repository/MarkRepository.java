package com.ptit.repository;

import com.ptit.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    @Query("SELECT m from Mark m where m.student_id = :studentID")
    Mark findAllByStudent_id(Long studentID);

    @Query("SELECT m from Mark m where m.assignment > 0 and m.attendance > 0 and m.test > 0")
    List<Mark> findAllMarkPass();

    @Query("SELECT m from Mark m where m.attendance = 0 or m.test = 0 or m.assignment = 0 ")
    List<Mark> findAllMarkFail();
}
