package course.management.DTOs.providedCoursesDTOs;

import javax.validation.constraints.NotNull;

public class ProvidedCourseUpdateDTO {
    @NotNull(message = "The course id can not be empty.")
    private Long course_id;
    @NotNull(message = "The teacher id can not be empty.")
    private Long teacher_id;


    // Setters & Getters

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }
}
