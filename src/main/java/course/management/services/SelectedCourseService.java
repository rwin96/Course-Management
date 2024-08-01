package course.management.services;

import course.management.DTOs.SelectedCourseDTOs.SelectedCourseCreateDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseUpdateDTO;

import java.util.List;

public interface SelectedCourseService {
    SelectedCourseDTO createSelectedCourse(SelectedCourseCreateDTO selectedCourseDTO);

    SelectedCourseDTO getSelectedCourseById(Long id);

    List<SelectedCourseDTO> getAllSelectedCourses();

    SelectedCourseDTO updateSelectedCourse(Long id, SelectedCourseUpdateDTO selectedCourseDTO);

    void deleteSelectedCourseById(Long id);

    boolean isCourseProvidedByTeacher(Long selectedCourseId, Long teacherId);

}