package course.management.DTOs.departmentDTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DepartmentCreateDTO {
    @NotBlank(message = "Name can not blank.")
    @Size(min = 5, max = 48, message = "The name must have a length between 5 and 48 characters.")
    @Pattern(regexp = "^[a-zA-z0-9 ]+$", message = "The name only can have characters and numbers.")
    private String name;


    // Setters & Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
