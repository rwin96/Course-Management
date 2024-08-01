package course.management.DTOs.courseDTOs;

import course.management.DTOs.providedCoursesDTOs.ProvidedCourseDTO;
import course.management.enums.Converter.CreditConverter;
import course.management.enums.Credit;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CourseDTO {
    private Long id;
    @NotBlank(message = "Name can not blank.")
    @Length(min = 5, max = 68, message = "The name must have a length between 5 and 68 characters.")
    private String name;
    @Convert(converter = CreditConverter.class)
    @NotNull(message = "Credit can not blank.")
    private Credit credit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
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
