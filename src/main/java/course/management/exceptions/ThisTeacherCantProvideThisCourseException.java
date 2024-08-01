package course.management.exceptions;

public class ThisTeacherCantProvideThisCourseException extends RuntimeException {
    public ThisTeacherCantProvideThisCourseException(String message) {
        super(message);
    }
}
