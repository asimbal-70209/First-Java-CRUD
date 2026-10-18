package com.asimbal.first_java_crud;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // READ - List all students
    @GetMapping
    public List<Student> getStudents() {
        return repository.findAll();
    }

    // CREATE - Add a student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    // UPDATE - Edit a student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student details) {

        return repository.findById(id)
            .map(student -> {
                student.setName(details.getName());
                student.setEmail(details.getEmail());
                return ResponseEntity.ok(repository.save(student));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Remove a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
