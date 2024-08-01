package course.management.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Teacher extends UserBase {
    @OneToMany(mappedBy = "teacher")
    List<ProvidedCourse> providedCourses;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Long personalId;

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

    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<ProvidedCourse> getProvidedCourses() {
        return providedCourses;
    }

    public void setProvidedCourses(List<ProvidedCourse> providedCourses) {
        this.providedCourses = providedCourses;
    }

}
