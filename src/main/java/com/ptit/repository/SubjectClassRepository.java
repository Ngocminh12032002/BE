package com.ptit.repository;

import com.ptit.model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectClassRepository extends JpaRepository<SubjectClass, Long> {
    @Query("SELECT i FROM SubjectClass i where i.teacher_id = :id")
    List<SubjectClass> findAllByTeacherID(Long id);

    @Query("SELECT i FROM SubjectClass i where i.teacher_id = :teacherId and i.course_id = :courseId")
    List<SubjectClass> findAllByTeacherIdAndCourseId(Long teacherId, Long courseId);
}
