package com.springboot.rest.example.CSV;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.example.model.Student;
import com.springboot.rest.example.repository.StudentRepository;

@Service
public class CSVServiceForStudent {
  @Autowired
  StudentRepository repository;
  
  CSVHelper csvHelper = new CSVHelper();

  public void save(MultipartFile file) {
    try {
      List<Student> students = csvHelper.csvToStudents(file.getInputStream());
      repository.saveAll(students);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Student> students = repository.findAll();

    ByteArrayInputStream in = csvHelper.studentsToCSV(students);
    return in;
  }

  public List<Student> getAllStudents() {
    return repository.findAll();
  }
}