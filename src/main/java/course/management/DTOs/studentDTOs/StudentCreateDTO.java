package course.management.DTOs.studentDTOs;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudentCreateDTO {
    @Column(unique = true)
    @NotNull(message = "Student Number can not be null.")
    private Long studentNumber;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String firstName;
    @Length(max = 48)
    @NotBlank(message = "Name can not blank.")
    private String lastName;
    @NotBlank(message = "National Id can not blank.")
    @Column(unique = true)
    private String nationalId;
    @Length(max = 128)
    private String address;
    @NotNull(message = "Department Id can not null.")
    private Long departmentId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
