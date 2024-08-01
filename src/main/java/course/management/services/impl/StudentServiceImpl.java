package course.management.services.impl;

import course.management.DTOs.studentDTOs.StudentCreateDTO;
import course.management.DTOs.studentDTOs.StudentDTO;
import course.management.DTOs.studentDTOs.StudentUpdateDTO;
import course.management.entities.Department;
import course.management.entities.SelectedCourse;
import course.management.entities.Student;
import course.management.exceptions.DuplicateRecordException;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.YouDontHaveAccessException;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.CourseRepository;
import course.management.repositories.DepartmentRepository;
import course.management.repositories.ProvidedCourseRepository;
import course.management.repositories.StudentRepository;
import course.management.services.SelectedCourseService;
import course.management.services.StudentService;
import course.management.util.DTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private DepartmentRepository departmentRepository;
    private CourseRepository courseRepository;
    private ProvidedCourseRepository providedCourseRepository;
    private SelectedCourseService selectedCourseService;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository, ProvidedCourseRepository providedCourseRepository, SelectedCourseService selectedCourseService) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.providedCourseRepository = providedCourseRepository;
        this.selectedCourseService = selectedCourseService;
    }

    @Override
    public StudentDTO createStudent(StudentCreateDTO studentDTO) {
        checkDuplicate(studentDTO.getStudentNumber(), studentDTO.getNationalId());
        Student student = convertCreateDTOToEntity(studentDTO);
        studentRepository.save(student);
        return DTOConvertor.convertStudentEntityToDTO(student);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")) && userDetails.getId() != id)
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        Student student = studentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + id));
        return DTOConvertor.convertStudentEntityToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students.stream().map(DTOConvertor::convertStudentEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getAllStudentsByDepartmentId(Long departmentId) {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students.stream().filter(student -> student.getDepartment().getId() == departmentId).map(student -> DTOConvertor.convertStudentEntityToDTO(student)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentUpdateDTO studentDTO) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + id));

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")) && userDetails.getId() != student.getId())
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD")) && userDetails.getDepartmentId() != student.getDepartment().getId())
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        boolean changed = false;

        if (studentDTO.getStudentNumber() != null) {
            checkDuplicate(studentDTO.getStudentNumber());
            student.setStudentNumber(studentDTO.getStudentNumber());
            changed = true;
        }

        if (studentDTO.getFirstName() != null && !studentDTO.getFirstName().isBlank() && !studentDTO.getFirstName().isEmpty()) {
            student.setFirstName(studentDTO.getFirstName());
            changed = true;
        }

        if (studentDTO.getLastName() != null && !studentDTO.getLastName().isBlank() && !studentDTO.getLastName().isEmpty()) {
            student.setLastName(studentDTO.getLastName());
            changed = true;
        }

        if (studentDTO.getNationalId() != null && !studentDTO.getNationalId().isBlank() && !studentDTO.getNationalId().isEmpty()) {
            checkDuplicate(studentDTO.getNationalId());
            student.setNationalId(studentDTO.getNationalId());
            changed = true;
        }

        if (studentDTO.getAddress() != null && !studentDTO.getAddress().isBlank() && !studentDTO.getAddress().isEmpty()) {
            student.setAddress(studentDTO.getAddress());
            changed = true;
        }

        if (changed)
            studentRepository.save(student);

        return DTOConvertor.convertStudentEntityToDTO(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + id));
        studentRepository.deleteById(id);
    }

    @Override
    public Float getAverage(Long studentId) {
        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT")) && userDetails.getId() != studentId)
            throw new YouDontHaveAccessException("Sorry. You dont have access!");

        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_HEAD"))) {
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new RecordNotFoundException("Student not found. ID: " + studentId));
            if (student.getDepartment().getId() != userDetails.getDepartmentId())
                throw new YouDontHaveAccessException("Sorry. You dont have access!");
        }


        List<SelectedCourse> selectedCourses = studentRepository.getAllSelectedCourseByStudentId(studentId);
        Double sum = selectedCourses.stream().map(selectedCourse -> selectedCourse.getScore() * selectedCourse.getProvidedCourse().getCourse().getCredit().getId()).mapToDouble(Double::valueOf).sum();
        Integer sumOfCredits = selectedCourses.stream().map(selectedCourse -> selectedCourse.getProvidedCourse().getCourse().getCredit().getId()).mapToInt(Integer::intValue).sum();

        return (float) (sum / (float) sumOfCredits);
    }

    private void checkDuplicate(Long studentNumber, String nationalId) {
        if (studentRepository.existsByStudentNumberAndNationalId(studentNumber, nationalId))
            throw new DuplicateRecordException("This student has already been added.");
    }

    private void checkDuplicate(Long studentNumber) {
        if (studentRepository.existsByStudentNumber(studentNumber))
            throw new DuplicateRecordException("This student has already been added.");
    }

    private void checkDuplicate(String nationalId) {
        if (studentRepository.existsByNationalId(nationalId))
            throw new DuplicateRecordException("This student has already been added.");
    }

    private Student convertCreateDTOToEntity(StudentCreateDTO studentDTO) {
        Student student = new Student();
        student.setStudentNumber(studentDTO.getStudentNumber());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setNationalId(studentDTO.getNationalId());

        if (studentDTO.getAddress() != null)
            student.setAddress(studentDTO.getAddress());

        Department department = departmentRepository.findById(studentDTO.getDepartmentId()).orElse(null);
        student.setDepartment(department);

        return student;
    }
}
