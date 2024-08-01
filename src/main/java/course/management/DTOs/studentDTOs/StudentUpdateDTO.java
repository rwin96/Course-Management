package course.management.DTOs.studentDTOs;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudentUpdateDTO {
    @NotBlank(message = "Student Number can not be blank.")
    private Long studentNumber;
    @Length(max = 48)
    @NotNull(message = "Name can not null.")
    @NotBlank(message = "Name can not blank.")
    private String firstName;
    @Length(max = 48)
    @NotNull(message = "Name can not null.")
    @NotBlank(message = "Name can not blank.")
    private String lastName;
    @NotNull(message = "National Id can not null.")
    @NotBlank(message = "National Id can not blank.")
    private String nationalId;
    @Length(max = 128)
    private String address;

    @NotBlank(message = "Password can not be blank.")
    private String password;


    // Setters & Getters


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
