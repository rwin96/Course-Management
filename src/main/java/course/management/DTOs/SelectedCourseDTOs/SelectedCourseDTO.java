package course.management.DTOs.SelectedCourseDTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SelectedCourseDTO {
    private Long id;

    @Min(value = 0)
    @Max(value = 20)
    private Float score;
    @NotNull(message = "The student id can not be empty.")
    private Long student_id;
    @NotNull(message = "The provided course id can not be empty.")
    private Long providedCourse_id;


    // Setters & Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getProvidedCourse_id() {
        return providedCourse_id;
    }

    public void setProvidedCourse_id(Long providedCourse_id) {
        this.providedCourse_id = providedCourse_id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }
}
