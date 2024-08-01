package course.management.DTOs.departmentDTOs;

import course.management.DTOs.courseDTOs.CourseDTO;
import course.management.DTOs.studentDTOs.StudentDTO;
import course.management.DTOs.teacherDTOs.TeacherDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class DepartmentDTO {
    private Long id;
    @NotBlank(message = "Name can not blank.")
    @Size(min = 5, max = 48, message = "The name must have a length between 5 and 48 characters.")
    @Pattern(regexp = "^[a-zA-z0-9 ]+$", message = "The name only can have characters and numbers.")
    private String name;

    private List<CourseDTO> courses;
    private List<StudentDTO> students;
    private List<TeacherDTO> teachers;
    private Long head_id;


    // Setters & Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHead_id() {
        return head_id;
    }

    public void setHead_id(Long head_id) {
        this.head_id = head_id;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherDTO> teachers) {
        this.teachers = teachers;
    }
}
