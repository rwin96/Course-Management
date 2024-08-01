package course.management.entities;

import course.management.enums.Converter.CreditConverter;
import course.management.enums.Credit;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max = 68)
    @Column(unique = true)
    private String name;

    @Convert(converter = CreditConverter.class)
    private Credit credit;


    @OneToMany(mappedBy = "course")
    private List<ProvidedCourse> providedCourses;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


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

    public List<ProvidedCourse> getProvidedCourses() {
        return providedCourses;
    }

    public void setProvidedCourses(List<ProvidedCourse> providedCourses) {
        this.providedCourses = providedCourses;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
