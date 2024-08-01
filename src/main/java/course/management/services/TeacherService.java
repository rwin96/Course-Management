package course.management.services;

import course.management.DTOs.teacherDTOs.TeacherCreateDTO;
import course.management.DTOs.teacherDTOs.TeacherDTO;
import course.management.DTOs.teacherDTOs.TeacherUpdateDTO;

import java.util.List;

public interface TeacherService {
    TeacherDTO createTeacher(TeacherCreateDTO teacherDTO);

    TeacherDTO getTeacherById(Long id);

    List<TeacherDTO> getAllTeachers();

    List<TeacherDTO> getAllTeachersByDepartmentId(Long departmentId);

    TeacherDTO updateTeacher(Long id, TeacherUpdateDTO teacherDTO);

    void deleteTeacher(Long id);

}
