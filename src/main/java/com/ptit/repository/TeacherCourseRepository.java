package com.ptit.repository;

import com.ptit.model.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    @Query("SELECT i from TeacherCourse i WHERE i.teacher_id = :teacherId ")
    List<TeacherCourse> findAllByTeacher_id(Long teacherId);
}
