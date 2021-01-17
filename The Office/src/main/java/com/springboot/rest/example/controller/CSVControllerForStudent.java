package com.springboot.rest.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.example.CSV.CSVHelper;
import com.springboot.rest.example.CSV.CSVServiceForStudent;
import com.springboot.rest.example.exception.ResponseMessage;
import com.springboot.rest.example.model.Student;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVControllerForStudent {

  @Autowired
  CSVServiceForStudent studentService;
  
  @PostMapping("/students/upload")
  public ResponseEntity<ResponseMessage> uploadStudentsFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (CSVHelper.hasCSVFormat(file)) {
      try {
    	  studentService.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
  }
  
  @GetMapping("/students")
  public ResponseEntity<List<Student>> getAllStudents() {
    try {
      List<Student> students = studentService.getAllStudents();

      if (students.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(students, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/student/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadQuotesFile(@PathVariable String fileName) {
    InputStreamResource file = new InputStreamResource(studentService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }
}