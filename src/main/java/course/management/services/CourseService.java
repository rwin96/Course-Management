package course.management.services;

import course.management.DTOs.courseDTOs.CourseCreateDTO;
import course.management.DTOs.courseDTOs.CourseDTO;
import course.management.DTOs.courseDTOs.CourseUpdateDTO;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseCreateDTO courseDTO);

    CourseDTO getCourseById(Long id);

    List<CourseDTO> getAllCourses();

    CourseDTO updateCourse(Long id, CourseUpdateDTO courseDTO);

    void deleteCourse(Long id);
}
