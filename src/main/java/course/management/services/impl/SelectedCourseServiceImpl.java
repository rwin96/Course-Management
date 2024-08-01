package course.management.services.impl;

import course.management.DTOs.SelectedCourseDTOs.SelectedCourseCreateDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseDTO;
import course.management.DTOs.SelectedCourseDTOs.SelectedCourseUpdateDTO;
import course.management.entities.ProvidedCourse;
import course.management.entities.SelectedCourse;
import course.management.entities.Student;
import course.management.entities.Teacher;
import course.management.exceptions.DuplicateRecordException;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.ProvidedCourseRepository;
import course.management.repositories.SelectedCourseRepository;
import course.management.repositories.StudentRepository;
import course.management.repositories.TeacherRepository;
import course.management.services.SelectedCourseService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SelectedCourseServiceImpl implements SelectedCourseService {

    @Autowired
    private SelectedCourseRepository selectedCourseRepository;

    @Autowired
    private ProvidedCourseRepository providedCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    @Override
    public SelectedCourseDTO createSelectedCourse(SelectedCourseCreateDTO selectedCourseDTO) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Student student = studentRepository.findById(selectedCourseDTO.getStudent_id()).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + selectedCourseDTO.getStudent_id()));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")) && userDetails.getId() != student.getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        ProvidedCourse providedCourse = providedCourseRepository.findById(selectedCourseDTO.getProvidedCourse_id()).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + selectedCourseDTO.getProvidedCourse_id()));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != providedCourse.getTeacher().getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");


        checkDuplicate(selectedCourseDTO.getStudent_id(), selectedCourseDTO.getProvidedCourse_id());
        SelectedCourse selectedCourse = convertSelectedCourseCreateDTOToEntity(selectedCourseDTO);
        selectedCourseRepository.save(selectedCourse);
        return DTOConvertor.convertSelectedCourseEntityToDTO(selectedCourse);
    }


    @Override
    public SelectedCourseDTO getSelectedCourseById(Long id) {
        SelectedCourse selectedCourse = selectedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Selected Course not found. ID: " + id));
        return DTOConvertor.convertSelectedCourseEntityToDTO(selectedCourse);
    }

    @Override
    public List<SelectedCourseDTO> getAllSelectedCourses() {
        List<SelectedCourse> selectedCourses = new ArrayList<>();
        if (needDepartmentFilter()) {
            CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            selectedCourseRepository.findAllByDepartmentId(userDetails.getDepartmentId()).forEach(selectedCourses::add);
        } else {
            selectedCourseRepository.findAll().forEach(selectedCourses::add);
        }
        return selectedCourses.stream()
                .map(DTOConvertor::convertSelectedCourseEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SelectedCourseDTO updateSelectedCourse(Long id, SelectedCourseUpdateDTO selectedCourseDTO) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) && !isCourseProvidedByTeacher(id, userDetails.getId()))
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        SelectedCourse selectedCourse = selectedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Selected Course not found. ID: " + id));
        ProvidedCourse providedCourse = providedCourseRepository.findById(selectedCourse.getProvidedCourse().getId()).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + selectedCourse.getProvidedCourse().getId()));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != providedCourse.getTeacher().getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        boolean changed = false;

        if (selectedCourseDTO.getScore() != null) {
            selectedCourse.setScore(selectedCourseDTO.getScore());
            changed = true;
        }

        if (changed)
            selectedCourseRepository.save(selectedCourse);

        return DTOConvertor.convertSelectedCourseEntityToDTO(selectedCourse);
    }


    @Override
    public void deleteSelectedCourseById(Long id) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SelectedCourse selectedCourse = selectedCourseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Selected Course not found. ID: " + id));

        Student student = studentRepository.findById(selectedCourse.getStudent().getId()).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + selectedCourse.getStudent().getId()));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")) && userDetails.getId() != student.getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        ProvidedCourse providedCourse = providedCourseRepository.findById(selectedCourse.getProvidedCourse().getId()).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + selectedCourse.getProvidedCourse().getId()));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != providedCourse.getTeacher().getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry! You dont have access.");

        selectedCourseRepository.deleteById(id);
    }

    private void checkDuplicate(Long student_id, Long providedCourse_id) {
        if (selectedCourseRepository.findProvidedCoursesIdsByStudentId(student_id).contains(providedCourse_id))
            throw new DuplicateRecordException("This Course has already been selected.");
    }

    private SelectedCourse convertSelectedCourseCreateDTOToEntity(SelectedCourseCreateDTO selectedCourseDTO) {
        SelectedCourse selectedCourse = new SelectedCourse();

        selectedCourse.setStudent(studentRepository.findById(selectedCourseDTO.getStudent_id()).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + selectedCourseDTO.getStudent_id())));
        selectedCourse.setProvidedCourse(providedCourseRepository.findById(selectedCourseDTO.getProvidedCourse_id()).orElseThrow(() -> new RecordNotFoundException("Provided Course not found. ID: " + selectedCourseDTO.getProvidedCourse_id())));

        return selectedCourse;
    }

    @Override
    public boolean isCourseProvidedByTeacher(Long selectedCourseId, Long teacherId) {
        SelectedCourse selectedCourse = selectedCourseRepository.findById(selectedCourseId).orElseThrow(() -> new RecordNotFoundException("Selected course not found. ID: " + selectedCourseId));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + teacherId));

        return teacher.getProvidedCourses().contains(selectedCourse.getProvidedCourse());
    }

    private boolean needDepartmentFilter() {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> authorities = userDetails.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        if (authorities.contains("ROLE_HEAD") || authorities.contains("ROLE_TEACHER") || authorities.contains("ROLE_STUDENT"))
            return true;

        return false;
    }
}
