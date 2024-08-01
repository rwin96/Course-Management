package course.management.DTOs.teacherDTOs;

import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TeacherDTO {
    private Long id;
    @NotNull(message = "Personal Id can not be null.")
    private Long personalId;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String firstName;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String lastName;
    @NotBlank(message = "National Id can not blank.")
    private String nationalId;
    private List<ProvidedCourseDTO> providedCourses;
    @NotNull(message = "Department Id can not null.")
    private Long departmentId;


    // Setters & Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<ProvidedCourseDTO> getProvidedCourses() {
        return providedCourses;
    }

    public void setProvidedCourses(List<ProvidedCourseDTO> providedCourses) {
        this.providedCourses = providedCourses;
    }
}
