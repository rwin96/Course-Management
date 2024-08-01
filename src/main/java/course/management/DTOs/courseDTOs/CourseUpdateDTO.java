package course.management.DTOs.courseDTOs;

import course.management.enums.Converter.CreditConverter;
import course.management.enums.Credit;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CourseUpdateDTO {
    @NotBlank(message = "Name can not blank.")
    @Length(min = 5, max = 68, message = "The name must have a length between 5 and 68 characters.")
    private String name;
    @Convert(converter = CreditConverter.class)
    @NotNull(message = "Credit can not blank.")
    private Credit credit;


    // Setters & Getters

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

}
