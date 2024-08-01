package course.management.controllers.api;

import course.management.DTOs.providedCoursesDTOs.ProvidedCourseCreateDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseUpdateDTO;
import course.management.services.ProvidedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/provided-course")
public class ProvidedCourseController {
    @Autowired
    ProvidedCourseService providedCourseService;

    @PostMapping(name = "CreateProvidedCourse")
    public ResponseEntity<ProvidedCourseDTO> createProvidedCourse(@Valid @RequestBody ProvidedCourseCreateDTO providedCourseCreateDTO) {
        ProvidedCourseDTO providedCourseDTO = providedCourseService.createProvidedCourse(providedCourseCreateDTO);
        return new ResponseEntity<>(providedCourseDTO, HttpStatus.CREATED);
    }

    @GetMapping(name = "GetProvidedCourse", value = "/{id}")
    public ResponseEntity<ProvidedCourseDTO> getProvidedCourse(@PathVariable Long id) {
        ProvidedCourseDTO providedCourseDTO = providedCourseService.getProvidedCourseById(id);
        return new ResponseEntity<>(providedCourseDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllProvidedCourses")
    public ResponseEntity<List<ProvidedCourseDTO>> getAllProvidedCourses() {
        List<ProvidedCourseDTO> providedCourseDTOS = providedCourseService.getAllProvidedCourses();
        return new ResponseEntity<>(providedCourseDTOS, HttpStatus.OK);
    }

    @PutMapping(name = "UpdateProvidedCourse", value = "/{id}")
    public ResponseEntity<ProvidedCourseDTO> updateProvidedCourse(@PathVariable Long id,@Valid @RequestBody ProvidedCourseUpdateDTO providedCourseDTO) {
        ProvidedCourseDTO providedCourse = providedCourseService.updateProvidedCourse(id, providedCourseDTO);
        return new ResponseEntity<>(providedCourse, HttpStatus.OK);
    }

    @DeleteMapping(name = "DeleteProvidedCourse", value = "/{id}")
    public ResponseEntity<String> deleteProvidedCourse(@PathVariable Long id) {
        providedCourseService.deleteProvidedCourseById(id);
        return new ResponseEntity<>(("ProvidedCourse with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }


    @GetMapping(name = "GetAverageOfProvidedCourse", value = "/{id}/avg")
    public ResponseEntity<Float> getAverageOfProvidedCourse(@PathVariable Long id) {
        Float avg = providedCourseService.getAverageOfCourse(id);
        return new ResponseEntity<>(avg, HttpStatus.OK);
    }
}
