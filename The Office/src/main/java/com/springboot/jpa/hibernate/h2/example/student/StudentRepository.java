package com.springboot.jpa.hibernate.h2.example.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.example.student.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
