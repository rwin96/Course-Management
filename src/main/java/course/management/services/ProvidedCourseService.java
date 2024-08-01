package course.management.services;

import course.management.DTOs.providedCoursesDTOs.ProvidedCourseCreateDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseUpdateDTO;

import java.util.List;

public interface ProvidedCourseService {
    ProvidedCourseDTO createProvidedCourse(ProvidedCourseCreateDTO providedCourseDTO);

    ProvidedCourseDTO getProvidedCourseById(Long id);

    List<ProvidedCourseDTO> getAllProvidedCourses();

    ProvidedCourseDTO updateProvidedCourse(Long id, ProvidedCourseUpdateDTO providedCourseDTO);

    void deleteProvidedCourseById(Long id);

    Float getAverageOfCourse(Long providedCourseId);

}
