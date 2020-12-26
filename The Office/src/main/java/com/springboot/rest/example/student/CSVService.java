package com.springboot.rest.example.student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
  @Autowired
  StudentRepository repository;

  public void save(MultipartFile file) {
    try {
      List<Student> students = CSVHelper.csvToStudents(file.getInputStream());
      repository.saveAll(students);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Student> students = repository.findAll();

    ByteArrayInputStream in = CSVHelper.studentsToCSV(students);
    return in;
  }

  public List<Student> getAllStudents() {
    return repository.findAll();
  }
}