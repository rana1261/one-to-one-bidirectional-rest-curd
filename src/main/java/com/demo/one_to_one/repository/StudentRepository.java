package com.demo.one_to_one.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.one_to_one.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}

