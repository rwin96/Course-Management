package course.management.helper;

import course.management.exceptions.DuplicateRecordException;
import course.management.exceptions.RecordNotFoundException;
import course.management.exceptions.ThisTeacherCantProvideThisCourseException;
import course.management.exceptions.YouDontHaveAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({RecordNotFoundException.class, DuplicateRecordException.class, ThisTeacherCantProvideThisCourseException.class})
    public ResponseEntity<ErrorResponse> RecordNotFoundExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class, PersistenceException.class})
    public ResponseEntity<ErrorResponse> JPAAndHibernateValidationHandler(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(YouDontHaveAccessException.class)
    public ResponseEntity<ErrorResponse> YouDontHaveAccessExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionsHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage().substring(e.getMessage().lastIndexOf("[")).replace("[", "").replace("]", "").trim()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> EveryExceptionsHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
