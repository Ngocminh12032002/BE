package com.ptit.repository;

import com.ptit.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TeacherRepository  extends JpaRepository<Teacher, Long> {
    @Query("SELECT i from Teacher i WHERE i.username = :username AND i.password = :password")
    Teacher login(String username,String password);

}
