package course.management.DTOs.teacherDTOs;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class TeacherUpdateDTO {
    @NotBlank(message = "Personal Id can not be blank.")
    private Long personalId;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String firstName;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String lastName;
    @NotBlank(message = "National Id can not blank.")
    private String nationalId;

    @NotBlank(message = "Password can not be blank.")
    private String password;


    // Setters & Getters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
