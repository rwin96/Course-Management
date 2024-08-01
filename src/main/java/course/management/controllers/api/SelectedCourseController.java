package course.management.controllers.api;

import course.management.DTOs.SelectedCourseDTOs.SelectedCourseCreateDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseUpdateDTO;
import course.management.services.SelectedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/selected-course")
public class SelectedCourseController {
    @Autowired
    SelectedCourseService selectedCourseService;

    @PostMapping(name = "CreateSelectedCourse")
    public ResponseEntity<SelectedCourseDTO> createSelectedCourse(@Valid @RequestBody SelectedCourseCreateDTO selectedCourseCreateDTO) {
        SelectedCourseDTO selectedCourseDTO = selectedCourseService.createSelectedCourse(selectedCourseCreateDTO);
        return new ResponseEntity<>(selectedCourseDTO, HttpStatus.CREATED);
    }

    @GetMapping(name = "GetSelectedCourse", value = "/{id}")
    public ResponseEntity<SelectedCourseDTO> getSelectedCourse(@PathVariable Long id) {
        SelectedCourseDTO selectedCourseDTO = selectedCourseService.getSelectedCourseById(id);
        return new ResponseEntity<>(selectedCourseDTO, HttpStatus.OK);
    }

    @GetMapping(name = "GetAllSelectedCourses")
    public ResponseEntity<List<SelectedCourseDTO>> getAllSelectedCourses() {
        List<SelectedCourseDTO> selectedCourseDTOS = selectedCourseService.getAllSelectedCourses();
        return new ResponseEntity<>(selectedCourseDTOS, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('HEAD') or (hasRole('TEACHER') and @selectedCourseServiceImpl.isCourseProvidedByTeacher(#id , authentication.getPrincipal().id))")
    @PutMapping(name = "UpdateSelectedCourse", value = "/{id}")
    public ResponseEntity<SelectedCourseDTO> updateSelectedCourse(@PathVariable Long id,@Valid @RequestBody SelectedCourseUpdateDTO selectedCourseDTO) {
        SelectedCourseDTO selectedCourse = selectedCourseService.updateSelectedCourse(id, selectedCourseDTO);
        return new ResponseEntity<>(selectedCourse, HttpStatus.OK);
    }

    @DeleteMapping(name = "DeleteSelectedCourse", value = "/{id}")
    public ResponseEntity<String> deleteSelectedCourse(@PathVariable Long id) {
        selectedCourseService.deleteSelectedCourseById(id);
        return new ResponseEntity<>(("SelectedCourse with ID '" + id + "' has been deleted successfully."), HttpStatus.OK);
    }
}
