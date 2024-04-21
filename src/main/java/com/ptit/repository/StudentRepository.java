package com.ptit.repository;

import com.ptit.model.Student;
import com.ptit.model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT COUNT(s) from Student s where s.subject_class_id = :subjectClassId")
    Long getNumberStudent(Long subjectClassId);

    @Query("SELECT s from Student s where s.subject_class_id = :subjectClassId")
    List<Student> findAllBySubject_class_id(Long subjectClassId);

    @Query("select s from Student s where s.id = :idStudent and s.subject_class_id = :subjectClassId")
    Student findStudentByIdAndSubject_class_id(Long idStudent, Long subjectClassId);

    @Query("select s from Student s where s.subject_class_id = :subjectClassId and s.code like %:keyword% or s.name like %:keyword% or s.email like %:keyword%")
    List<Student> findAllBySubject_class_idAndKey(Long subjectClassId, String keyword);

    @Query("select s from Student s where s.id = :idStudent and s.subject_class_id = :subjectClassId and (s.code like %:keyword% or s.name like %:keyword% or s.email like %:keyword%)")
    Optional<Student> findStudentByIdAndSubject_class_idAndKey(Long idStudent, Long subjectClassId, String keyword);
}
