package deposit.exception;

public class OldAndNewPasswordEqualException extends RuntimeException {
    public OldAndNewPasswordEqualException(String message) {
        super(message);
    }
}
