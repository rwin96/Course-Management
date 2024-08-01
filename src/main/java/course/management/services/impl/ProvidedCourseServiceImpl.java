package course.management.services.impl;

import course.management.DTOs.providedCoursesDTOs.ProvidedCourseCreateDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseUpdateDTO;
import course.management.entities.Course;
import course.management.entities.ProvidedCourse;
import course.management.entities.Teacher;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.ThisTeacherCantProvideThisCourseException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.CourseRepository;
import course.management.repositories.ProvidedCourseRepository;
import course.management.repositories.TeacherRepository;
import course.management.services.ProvidedCourseService;
import course.management.services.StudentService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvidedCourseServiceImpl implements ProvidedCourseService {

    @Autowired
    ProvidedCourseRepository providedCourseRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentService studentService;

    @Override
    public ProvidedCourseDTO createProvidedCourse(ProvidedCourseCreateDTO providedCourseDTO) {
        Teacher teacher = teacherRepository.findById(providedCourseDTO.getTeacher_id()).orElseThrow(() -> new RecordNotFoundException("Teacher not found. Id: " + providedCourseDTO.getTeacher_id()));
        Course course = courseRepository.findById(providedCourseDTO.getCourse_id()).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + providedCourseDTO.getCourse_id()));
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) && userDetails.getDepartmentId() != teacher.getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != teacher.getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        if (teacher.getDepartment().getId() != course.getDepartment().getId())
            throw new ThisTeacherCantProvideThisCourseException("This teacher (ID: " + teacher.getId() + ") can not provide this course (ID: " + course.getId() + ").");

        ProvidedCourse providedCourse = convertCreateDTOToEntity(providedCourseDTO);

        providedCourse = providedCourseRepository.save(providedCourse);

        return DTOConvertor.convertProvidedCourseEntityToDTO(providedCourse);
    }

    @Override
    public ProvidedCourseDTO getProvidedCourseById(Long id) {
        ProvidedCourse providedCourse = providedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + id));
        return DTOConvertor.convertProvidedCourseEntityToDTO(providedCourse);
    }

    @Override
    public List<ProvidedCourseDTO> getAllProvidedCourses() {
        List<ProvidedCourse> providedCourses = new ArrayList<>();
        providedCourseRepository.findAll().forEach(providedCourses::add);
        return providedCourses.stream()
                .map(DTOConvertor::convertProvidedCourseEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProvidedCourseDTO updateProvidedCourse(Long id, ProvidedCourseUpdateDTO providedCourseDTO) {
        ProvidedCourse providedCourse = providedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + id));
        checkUserDepartment(providedCourse.getTeacher().getDepartment().getId());
        boolean changed = false;

        if (providedCourseDTO.getCourse_id() != null) {
            providedCourse.setCourse(courseRepository.findById(providedCourseDTO.getCourse_id()).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + providedCourseDTO.getCourse_id())));
            changed = true;
        }
        if (providedCourseDTO.getTeacher_id() != null) {
            providedCourse.setTeacher(teacherRepository.findById(providedCourseDTO.getTeacher_id()).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + providedCourseDTO.getTeacher_id())));
            changed = true;
        }

        if (changed)
            providedCourseRepository.save(providedCourse);

        return DTOConvertor.convertProvidedCourseEntityToDTO(providedCourse);
    }

    @Override
    public void deleteProvidedCourseById(Long id) {
        ProvidedCourse providedCourse = providedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + id));
        checkUserDepartment(providedCourse.getTeacher().getDepartment().getId());
        providedCourseRepository.deleteById(id);
    }

    private ProvidedCourse convertCreateDTOToEntity(ProvidedCourseCreateDTO providedCourseDTO) {
        ProvidedCourse providedCourse = new ProvidedCourse();

        providedCourse.setCourse(courseRepository.findById(providedCourseDTO.getCourse_id()).orElseThrow(() -> new RecordNotFoundException("Course not found. ID: " + providedCourseDTO.getCourse_id())));
        providedCourse.setTeacher(teacherRepository.findById(providedCourseDTO.getTeacher_id()).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + providedCourseDTO.getTeacher_id())));

        return providedCourse;
    }

    @Override
    public Float getAverageOfCourse(Long providedCourseId) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) && !teacherRepository.getAllProvidedCoursesIdByTeacherId(userDetails.getId()).contains(providedCourseId)) {
            throw new YouDontHaveAccessException("Sorry! You dont have access.");
        }

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD"))) {
            ProvidedCourse providedCourse = providedCourseRepository.findById(providedCourseId).orElseThrow(() -> new RecordNotFoundException("ProvidedCourse not found. ID: " + providedCourseId));
            if (providedCourse.getCourse().getDepartment().getId() != userDetails.getDepartmentId())
                throw new YouDontHaveAccessException("Sorry! You dont have access.");
        }


        List<Long> studentsIds = teacherRepository.getAllStudentsIdsByProvidedCourseId(providedCourseId);
        List<Float> studentsAverages = studentsIds.stream().map(aLong -> studentService.getAverage(aLong)).toList();
        Float finalAvg = (float) (studentsAverages.stream().mapToDouble(Double::valueOf).sum()) / (float) studentsAverages.size();
        return finalAvg;
    }

    private void checkUserDepartment(Long departmentId) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) || userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD"))) && userDetails.getDepartmentId() != departmentId)
            throw new YouDontHaveAccessException("Sorry! You dont have access.");
    }
}
