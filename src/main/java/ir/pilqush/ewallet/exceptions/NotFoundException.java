package ir.pilqush.ewallet.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {

        super(message);
    }

    public EnumException getErrorEnum() {
        return null;
    }
}