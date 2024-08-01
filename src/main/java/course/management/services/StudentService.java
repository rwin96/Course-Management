package course.management.services;

import course.management.DTOs.studentDTOs.StudentCreateDTO;
import course.management.DTOs.studentDTOs.StudentDTO;
import course.management.DTOs.studentDTOs.StudentUpdateDTO;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentCreateDTO studentDTO);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents();

    List<StudentDTO> getAllStudentsByDepartmentId(Long departmentId);

    StudentDTO updateStudent(Long id, StudentUpdateDTO studentDTO);

    void deleteStudent(Long id);

    Float getAverage(Long studentId);
}
