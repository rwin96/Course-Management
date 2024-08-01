package course.management.controllers.api;

import course.management.DTOs.teacherDTOs.TeacherCreateDTO;
import course.management.DTOs.teacherDTOs.TeacherDTO;
import course.management.DTOs.teacherDTOs.TeacherUpdateDTO;
import course.management.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @PostMapping(name = "CreateTeacher")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        TeacherDTO teacherDTO = teacherService.createTeacher(teacherCreateDTO);
        return new ResponseEntity<>(teacherDTO, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD') or (hasRole('TEACHER') and #id == authentication.principal.id)")
    @GetMapping(name = "GetTeacher", value = "/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllTeachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teacherDTOS = teacherService.getAllTeachers();
        return new ResponseEntity<>(teacherDTOS, HttpStatus.OK);
    }

    @PutMapping(name = "UpdateTeacher", value = "/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id,@Valid @RequestBody TeacherUpdateDTO teacherUpdateDTO) {
        TeacherDTO teacherDTO = teacherService.updateTeacher(id, teacherUpdateDTO);
        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }


    @DeleteMapping(name = "DeleteTeacher", value = "/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(("Teacher with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }

}
