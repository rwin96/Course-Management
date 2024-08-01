package course.management.DTOs.SelectedCourseDTOs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SelectedCourseUpdateDTO {
    @Min(value = 0)
    @Max(value = 20)
    private Float score;


    // Setters & Getters

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
