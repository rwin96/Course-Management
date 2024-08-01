package course.management.util;

import course.management.DTOs.SelectedCourseDTOs.SelectedCourseDTO;
import course.management.DTOs.courseDTOs.CourseDTO;
import course.management.DTOs.departmentDTOs.DepartmentDTO;
import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import course.management.DTOs.studentDTOs.StudentDTO;
import course.management.DTOs.teacherDTOs.TeacherDTO;
import course.management.entities.*;

import java.util.stream.Collectors;

public class DTOConvertor {

    // Course
    public static Course convertCourseDTOToEntity(CourseDTO courseDTO) {
        Course course = new Course();

        course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        course.setCredit(courseDTO.getCredit());

        return course;
    }

    public static CourseDTO convertCourseEntityToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCredit(course.getCredit());

        if (course.getProvidedCourses() != null) {
            courseDTO.setProvidedCourses(course.getProvidedCourses().stream()
                    .map(DTOConvertor::convertProvidedCourseEntityToDTO)
                    .collect(Collectors.toList()));
        }

        courseDTO.setDepartmentId(course.getDepartment().getId());

        return courseDTO;
    }


    // Student
    public static Student convertStudentDTOToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setStudentNumber(studentDTO.getStudentNumber());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setNationalId(studentDTO.getNationalId());

        if (studentDTO.getAddress() != null)
            student.setAddress(studentDTO.getAddress());

        if (studentDTO.getSelectedCourses() != null)
            student.setSelectedCourses(studentDTO.getSelectedCourses().stream().map(DTOConvertor::convertSelectedCourseDTOToEntity).collect(Collectors.toList()));

        return student;
    }

    public static StudentDTO convertStudentEntityToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setStudentNumber(student.getStudentNumber());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setNationalId(student.getNationalId());

        if (student.getAddress() != null)
            studentDTO.setAddress(student.getAddress());

        studentDTO.setDepartmentId(student.getDepartment().getId());

        if (student.getSelectedCourses() != null)
            studentDTO.setSelectedCourses(student.getSelectedCourses().stream().map(DTOConvertor::convertSelectedCourseEntityToDTO).collect(Collectors.toList()));

        return studentDTO;
    }


    // Teacher
    public static Teacher convertTeacherDTOToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setPersonalId(teacherDTO.getPersonalId());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setNationalId(teacherDTO.getNationalId());

        if (teacherDTO.getProvidedCourses() != null) {
            teacher.setProvidedCourses(teacherDTO.getProvidedCourses().stream().map(DTOConvertor::convertProvidedCourseDTOToEntity).collect(Collectors.toList()));
        }

        return teacher;
    }

    public static TeacherDTO convertTeacherEntityToDTO(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setPersonalId(teacher.getPersonalId());
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setNationalId(teacher.getNationalId());
        teacherDTO.setDepartmentId(teacher.getDepartment().getId());

        if (teacher.getProvidedCourses() != null) {
            teacherDTO.setProvidedCourses(teacher.getProvidedCourses().stream().map(DTOConvertor::convertProvidedCourseEntityToDTO).collect(Collectors.toList()));
        }

        return teacherDTO;
    }


    // Department
    public static Department convertDepartmentDTOToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());

        return department;
    }

    public static DepartmentDTO convertDepartmentEntityToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());

        if (department.getHead() != null)
            departmentDTO.setHead_id(department.getHead().getId());

        if (department.getCourses() != null)
            departmentDTO.setCourses(department.getCourses().stream().map(DTOConvertor::convertCourseEntityToDTO).collect(Collectors.toList()));

        if (department.getStudents() != null)
            departmentDTO.setStudents(department.getStudents().stream().map(DTOConvertor::convertStudentEntityToDTO).collect(Collectors.toList()));

        if (department.getTeachers() != null)
            departmentDTO.setTeachers(department.getTeachers().stream().map(DTOConvertor::convertTeacherEntityToDTO).collect(Collectors.toList()));


        return departmentDTO;
    }


    // ProvidedCourse
    public static ProvidedCourseDTO convertProvidedCourseEntityToDTO(ProvidedCourse providedCourse) {
        ProvidedCourseDTO providedCourseDTO = new ProvidedCourseDTO();

        providedCourseDTO.setId(providedCourse.getId());
        providedCourseDTO.setCourse_id(providedCourse.getCourse().getId());
        providedCourseDTO.setTeacher_id(providedCourse.getTeacher().getId());

        return providedCourseDTO;
    }

    public static ProvidedCourse convertProvidedCourseDTOToEntity(ProvidedCourseDTO providedCourseDTO) {
        ProvidedCourse providedCourse = new ProvidedCourse();

        providedCourse.setId(providedCourseDTO.getId());

        return providedCourse;
    }


    // SelectedCourse
    public static SelectedCourseDTO convertSelectedCourseEntityToDTO(SelectedCourse selectedCourse) {
        SelectedCourseDTO selectedCourseDTO = new SelectedCourseDTO();

        selectedCourseDTO.setId(selectedCourse.getId());

        if (selectedCourse.getScore() != null)
            selectedCourseDTO.setScore(selectedCourse.getScore());

        selectedCourseDTO.setProvidedCourse_id(selectedCourse.getProvidedCourse().getId());
        selectedCourseDTO.setStudent_id(selectedCourse.getStudent().getId());

        return selectedCourseDTO;
    }


    public static SelectedCourse convertSelectedCourseDTOToEntity(SelectedCourseDTO selectedCourseDTO) {
        SelectedCourse selectedCourse = new SelectedCourse();

        selectedCourse.setId(selectedCourseDTO.getId());

        if (selectedCourseDTO.getScore() != null)
            selectedCourse.setScore(selectedCourseDTO.getScore());

        return selectedCourse;
    }
}
