package course.management.controllers.api;

import course.management.DTOs.studentDTOs.StudentCreateDTO;
import course.management.DTOs.studentDTOs.StudentDTO;
import course.management.DTOs.studentDTOs.StudentUpdateDTO;
import course.management.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping(name = "CreateStudent")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        StudentDTO studentDTO = studentService.createStudent(studentCreateDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD') or (hasRole('STUDENT') and #id == authentication.principal.id)")
    @GetMapping(name = "GetStudent", value = "/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllStudents")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOS = studentService.getAllStudents();
        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @PutMapping(name = "UpdateStudent", value = "/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,@Valid @RequestBody StudentUpdateDTO studentUpdateDTO) {
        StudentDTO studentDTO = studentService.updateStudent(id, studentUpdateDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping(name = "DeleteStudent", value = "/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(("Student with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }

    // TODO: edit params ,Change password
    @GetMapping(name = "GetAverage", value = "/{id}/avg")
    public ResponseEntity<Float> getAverage(@PathVariable Long id) {
        Float avg = studentService.getAverage(id);
        return new ResponseEntity<>(avg, HttpStatus.OK);
    }
}
