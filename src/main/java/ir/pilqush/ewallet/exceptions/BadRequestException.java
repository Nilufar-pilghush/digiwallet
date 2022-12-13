package ir.pilqush.ewallet.exceptions;

import java.util.Map;

public class BadRequestException extends ServiceException {

    public BadRequestException(EnumException errorEnum) {
        super(errorEnum);
    }

    public BadRequestException(EnumException errorEnum, Map<String, String> context, Throwable ex) {
        super(errorEnum, context, ex);
    }
}
