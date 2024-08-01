package course.management.controllers.api;

import course.management.DTOs.courseDTOs.CourseCreateDTO;
import course.management.DTOs.courseDTOs.CourseDTO;
import course.management.DTOs.courseDTOs.CourseUpdateDTO;
import course.management.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping(name = "CreateCourse")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        CourseDTO courseDTO = courseService.createCourse(courseCreateDTO);
        return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
    }

    @GetMapping(name = "GetCourse", value = "/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        CourseDTO courseDTO = courseService.getCourseById(id);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllCourses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courseDTOS = courseService.getAllCourses();
        return new ResponseEntity<>(courseDTOS, HttpStatus.OK);
    }

    @PutMapping(name = "UpdateCourse", value = "/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id,@Valid @RequestBody CourseUpdateDTO courseDTO) {
        CourseDTO course = courseService.updateCourse(id, courseDTO);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping(name = "DeleteCourse", value = "/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(("Course with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }
}
