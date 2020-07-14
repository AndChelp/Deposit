package deposit.exception.handler;

import deposit.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions() {
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(ContentNotFoundException.class)
    public void handleContentNotFound() {
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PermissionDeniedException.class)
    public void handlePermissionDenied() {
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class,
            UsernameAndEmailAlreadyExistException.class,
            OldAndNewPasswordEqualException.class,
            InvalidPasswordException.class})
    public ResponseEntity<String> handleRegistrationError(Exception ex) {
        return new ResponseEntity<>(ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST);
    }

}
