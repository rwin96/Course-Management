package course.management.services.impl;

import course.management.DTOs.teacherDTOs.TeacherCreateDTO;
import course.management.DTOs.teacherDTOs.TeacherDTO;
import course.management.DTOs.teacherDTOs.TeacherUpdateDTO;
import course.management.entities.Department;
import course.management.entities.Teacher;
import course.management.exceptions.DuplicateRecordException;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.CourseRepository;
import course.management.repositories.DepartmentRepository;
import course.management.repositories.TeacherRepository;
import course.management.services.StudentService;
import course.management.services.TeacherService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CourseRepository courseRepository;


    @Autowired
    ProvidedCourseServiceImpl providedCourseService;

    @Autowired
    StudentService studentService;

    @Override
    public TeacherDTO createTeacher(TeacherCreateDTO teacherDTO) {
        checkDuplicate(teacherDTO.getPersonalId(), teacherDTO.getNationalId());
        Teacher teacher = convertCreateDTOToEntity(teacherDTO);
        teacherRepository.save(teacher);
        return DTOConvertor.convertTeacherEntityToDTO(teacher);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) && userDetails.getId() != id)
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + id));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != teacher.getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        return DTOConvertor.convertTeacherEntityToDTO(teacher);
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacher -> DTOConvertor.convertTeacherEntityToDTO(teacher)).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getAllTeachersByDepartmentId(Long departmentId) {
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream()
                .filter(teacher -> teacher.getDepartment().getId() == departmentId)
                .map(teacher -> DTOConvertor.convertTeacherEntityToDTO(teacher))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherUpdateDTO teacherDTO) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + id));
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER")) && userDetails.getId() != teacher.getId())
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != teacher.getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        boolean changed = false;

        if (teacherDTO.getFirstName() != null && !teacherDTO.getFirstName().isBlank() && !teacherDTO.getFirstName().isEmpty()) {
            teacher.setFirstName(teacherDTO.getFirstName());
            changed = true;
        }

        if (teacherDTO.getLastName() != null && !teacherDTO.getLastName().isBlank() && !teacherDTO.getLastName().isEmpty()) {
            teacher.setLastName(teacherDTO.getLastName());
            changed = true;
        }

        if (teacherDTO.getNationalId() != null && !teacherDTO.getNationalId().isBlank() && !teacherDTO.getNationalId().isEmpty()) {
            checkDuplicate(teacherDTO.getNationalId());
            teacher.setNationalId(teacherDTO.getNationalId());
            changed = true;
        }

        if (teacherDTO.getPersonalId() != null) {
            checkDuplicate(teacherDTO.getPersonalId());
            teacher.setPersonalId(teacherDTO.getPersonalId());
            changed = true;
        }

        if (changed)
            teacherRepository.save(teacher);

        return DTOConvertor.convertTeacherEntityToDTO(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + id));
        teacherRepository.deleteById(id);
    }

    private void checkDuplicate(Long personalId, String nationalId) {
        if (teacherRepository.existsByPersonalIdAndNationalId(personalId, nationalId))
            throw new DuplicateRecordException("This teacher has already been added.");
    }

    private void checkDuplicate(Long personalId) {
        if (teacherRepository.existsByPersonalId(personalId))
            throw new DuplicateRecordException("This teacher has already been added.");
    }

    private void checkDuplicate(String nationalId) {
        if (teacherRepository.existsByNationalId(nationalId))
            throw new DuplicateRecordException("This teacher has already been added.");
    }

    private Teacher convertCreateDTOToEntity(TeacherCreateDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setPersonalId(teacherDTO.getPersonalId());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setNationalId(teacherDTO.getNationalId());

        Department department = departmentRepository.findById(teacherDTO.getDepartmentId()).orElseThrow(() -> new RecordNotFoundException("Department not found. ID: " + teacherDTO.getDepartmentId()));
        teacher.setDepartment(department);

        return teacher;
    }
}
