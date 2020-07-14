package deposit.exception;

public class UsernameAndEmailAlreadyExistException extends RuntimeException {
    public UsernameAndEmailAlreadyExistException(String message) {
        super(message);
    }
}
