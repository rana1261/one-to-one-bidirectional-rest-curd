package com.demo.one_to_one.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.one_to_one.exception.NotFoundException;
import com.demo.one_to_one.model.Student;
import com.demo.one_to_one.repository.StudentRepository;

@RestController
public class StudentController {
    @Autowired
    StudentRepository studentRepository;


    /*post a Student data without id and not post child(laptop) data*/
    @PostMapping(value = "students")
    public void saveStudent(@Valid @RequestBody Student student) {

        studentRepository.save(student);
    }

    /*only get all Students and not showing child(laptop) data*/
    @GetMapping(value = "students")
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }


    /*get a Student by ID and not showing child(laptop) data*/
    @GetMapping("/students/{id}")
    public Student getStudentByID(@PathVariable Long id) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            return optStudent.get();
        } else {
            throw new NotFoundException("Student not found with id " + id);
        }
    }
		/*
	@GetMapping(value="students/{id}")
	public Student getStudentById(@PathVariable Long id) {
		return studentRepository.getOne(id);
	}
	*/


    /*delete a Student and also delete its child(laptop) data*/
    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

    	/*@DeleteMapping(value="students/{id}")
	public void delete(@PathVariable Long id) {
		studentRepository.deleteById(id);
	}
	*/


    /*update only a Student without id and not update its child(laptop) data*/
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @Valid @RequestBody Student studentUpdated) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setStudentName(studentUpdated.getStudentName());
                    student.setStudentMark(studentUpdated.getStudentMark());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new NotFoundException("Student not found with id " + id));
    }

    /*	@PutMapping(value="students/update")
	public void updateStudent(@RequestBody Student student) {
		Student su=studentRepository.getOne(student.getSRoll());
		su.setSName(student.getSName());
		su.setSMark(student.getSMark());
		su.setLaptop(student.getLaptop());
		studentRepository.save(su);
	}*/
}
