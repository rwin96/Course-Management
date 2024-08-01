package course.management.DTOs.studentDTOs;

import course.management.DTOs.SelectedCourseDTOs.SelectedCourseDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class StudentDTO {
    @NotNull(message = "Student Number can not be null.")
    private Long id;
    private Long studentNumber;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String firstName;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String lastName;
    @NotBlank(message = "National Id can not blank.")
    private String nationalId;
    @Length(max = 128)
    private String address;
    private List<SelectedCourseDTO> selectedCourses;
    @NotNull(message = "Department Id can not null.")
    private Long departmentId;


    // Setters & Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<SelectedCourseDTO> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<SelectedCourseDTO> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
}
