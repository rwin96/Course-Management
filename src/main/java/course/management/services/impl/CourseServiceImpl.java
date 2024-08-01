package course.management.services.impl;

import course.management.DTOs.courseDTOs.CourseCreateDTO;
import course.management.DTOs.courseDTOs.CourseDTO;
import course.management.DTOs.courseDTOs.CourseUpdateDTO;
import course.management.entities.Course;
import course.management.exceptions.DuplicateRecordException;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.CourseRepository;
import course.management.repositories.DepartmentRepository;
import course.management.repositories.TeacherRepository;
import course.management.services.CourseService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public CourseDTO createCourse(CourseCreateDTO courseDTO) {
        checkUserDepartment(courseDTO.getDepartmentId());
        checkDuplicate(courseDTO.getName(), courseDTO.getDepartmentId());
        Course course = convertCreateDTOToEntity(courseDTO);
        courseRepository.save(course);
        return DTOConvertor.convertCourseEntityToDTO(course);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + id));
        checkUserDepartment(course.getDepartment().getId());
        return DTOConvertor.convertCourseEntityToDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses.stream()
                .map(DTOConvertor::convertCourseEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseUpdateDTO courseDTO) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + id));
        checkUserDepartment(course.getDepartment().getId());
        boolean changed = false;

        if (courseDTO.getName() != null || !courseDTO.getName().isEmpty() || !courseDTO.getName().isBlank()) {
            checkDuplicate(courseDTO.getName(), course.getDepartment().getId());
            course.setName(courseDTO.getName());
            changed = true;
        }

        if (courseDTO.getCredit() != null) {
            course.setCredit(courseDTO.getCredit());
            changed = true;
        }

        if (changed)
            course = courseRepository.save(course);

        return DTOConvertor.convertCourseEntityToDTO(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + id));
        checkUserDepartment(course.getDepartment().getId());
        courseRepository.deleteById(id);
    }

    private void checkDuplicate(String name, Long departmentId) {
        if (courseRepository.existsByNameAndDepartment(name, departmentRepository.findById(departmentId).orElseThrow(() -> new RecordNotFoundException("Department not found. ID: " + departmentId))))
            throw new DuplicateRecordException("This course has already been added.");
    }

    private void checkUserDepartment(Long departmentId) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) || userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD"))) && userDetails.getDepartmentId() != departmentId)
            throw new YouDontHaveAccessException("Sorry! You dont have access.");
    }

    private Course convertCreateDTOToEntity(CourseCreateDTO courseDTO) {
        Course course = new Course();

        course.setName(courseDTO.getName());
        course.setCredit(courseDTO.getCredit());
        course.setDepartment(departmentRepository.findById(courseDTO.getDepartmentId()).orElseThrow(() -> new RecordNotFoundException("Department not found. ID: " + courseDTO.getDepartmentId())));

        return course;
    }
}
