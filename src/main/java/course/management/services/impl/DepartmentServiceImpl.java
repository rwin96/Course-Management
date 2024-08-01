package course.management.services.impl;

import course.management.DTOs.departmentDTOs.DepartmentCreateDTO;
import course.management.DTOs.departmentDTOs.DepartmentDTO;
import course.management.DTOs.departmentDTOs.DepartmentUpdateDTO;
import course.management.entities.Department;
import course.management.entities.Student;
import course.management.entities.Teacher;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.DepartmentRepository;
import course.management.repositories.TeacherRepository;
import course.management.services.DepartmentService;
import course.management.services.StudentService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentService studentService;


    @Override
    public DepartmentDTO createDepartment(DepartmentCreateDTO departmentDTO) {
        checkDuplicate(departmentDTO.getName());
        Department department = convertCreateDTOToEntity(departmentDTO);
        departmentRepository.save(department);
        return DTOConvertor.convertDepartmentEntityToDTO(department);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != id)
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        Department department = departmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Department not found. ID : " + id));
        return DTOConvertor.convertDepartmentEntityToDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments.stream().map(department -> DTOConvertor.convertDepartmentEntityToDTO(department)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentDTO updateDepartment(Long id, DepartmentUpdateDTO departmentDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Department not found. ID: " + id));
        if (departmentDTO.getName().equals(department.getName()))
            departmentDTO.setName(null);
        checkDuplicate(departmentDTO.getName());
        boolean changed = false;

        if (departmentDTO.getName() != null && !departmentDTO.getName().isBlank() && !departmentDTO.getName().isEmpty()) {
            department.setName(departmentDTO.getName());
            changed = true;
        }

        if (departmentDTO.getHead_id() != null) {
            Teacher head = teacherRepository.findById(departmentDTO.getHead_id()).orElseThrow(() -> new RecordNotFoundException("Teacher not found. ID: " + departmentDTO.getHead_id()));

            if (department.getHead() != null) {
                teacherRepository.findById(department.getHead().getId()).ifPresent(teacher -> {
                    teacher.setRole("ROLE_TEACHER");
                    teacherRepository.save(teacher);
                });
            }

            head.setRole("ROLE_HEAD");
            teacherRepository.save(head);
            department.setHead(head);
            changed = true;
        }

        if (changed)
            departmentRepository.save(department);

        return DTOConvertor.convertDepartmentEntityToDTO(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Department not found. ID: " + id));
        departmentRepository.deleteById(id);
    }

    private void checkDuplicate(String name) {
        if (departmentRepository.existsByName(name))
            throw new RecordNotFoundException("This department has been already added.");
    }

    public Float getAvgById(Long id) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != id)
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        List<Student> students = departmentRepository.findAllStudentById(id);
        Double sumOfAverages = students.stream().map(student -> studentService.getAverage(student.getId())).mapToDouble(Double::valueOf).sum();
        Long countOfStudents = students.stream().count();
        return (float) (sumOfAverages / (float) countOfStudents);
    }

    private Department convertCreateDTOToEntity(DepartmentCreateDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());

        return department;
    }
}
