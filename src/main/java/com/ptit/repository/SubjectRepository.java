package com.ptit.repository;

import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("select i from Subject i where i.id = :subjectID")
    Subject findAllBySubjectId(Long subjectID);
}
