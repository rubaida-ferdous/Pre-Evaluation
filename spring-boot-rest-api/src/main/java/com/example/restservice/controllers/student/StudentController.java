package com.example.restservice.controllers.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.models.Student.Student;
import com.example.restservice.repository.StudentRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
    StudentRepository studentRepository;
	
	
	@PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
        	Student _student = studentRepository
                    .save(new Student(student.getInstitutionid(), student.getStudentid(), student.getFirstname(), student.getLastname(), student.getDateofbirth(), student.getGender(), student.getEmail()));
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	
	@GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String firstname) {
        try {
            List<Student> students = new ArrayList<Student>();
            studentRepository.findAll().forEach(students::add);
            
            
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        Optional<Student> studentData = studentRepository.findById(id);

        if (studentData.isPresent()) {
        	Student _student = studentData.get();
        	_student.setInstitutionid(student.getInstitutionid());
        	_student.setStudentid(student.getStudentid());
        	_student.setFirstname(student.getFirstname());
        	_student.setLastname(student.getLastname());
        	_student.setDateofbirth(student.getDateofbirth());
        	_student.setGender(student.getGender());
        	_student.setEmail(student.getEmail());
            return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	
	
	@DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        try {
        	studentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        Optional<Student> studentData = studentRepository.findById(id);

        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	


	@GetMapping("/students/{firstname}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable("firstname") String firstname) {
        List<Student> students = new ArrayList<Student>();
        
        studentRepository.findByFirstnameContaining(firstname).forEach(students::add);
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);

    }
	
	
	@DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        try {
        	studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
}