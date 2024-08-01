package course.management.exceptions;

public class YouDontHaveAccessException extends RuntimeException {
    public YouDontHaveAccessException(String message) {
        super(message);
    }
}
