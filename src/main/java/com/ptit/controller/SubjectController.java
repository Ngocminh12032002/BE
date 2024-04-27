package com.ptit.controller;

import com.ptit.DTO.response.StudentResponse;
import com.ptit.DTO.request.InputScoreRequest;
import com.ptit.model.Mark;
import com.ptit.model.Subject;
import com.ptit.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
}
