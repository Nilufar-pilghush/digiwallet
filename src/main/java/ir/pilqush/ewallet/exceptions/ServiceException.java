package ir.pilqush.ewallet.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceException extends RuntimeException {

    private final EnumException errorEnum;
    private Map<String, String> context = new HashMap<>();

    public ServiceException(EnumException enumException, Map<String, String> context, Throwable ex) {
        super(ex);
        this.errorEnum = enumException;
        this.context = Optional.ofNullable(context).orElse(Collections.emptyMap());
    }

    public ServiceException(EnumException errorEnum) {
        this.errorEnum = errorEnum;
        this.context = context;
    }

    public ServiceException(String message, EnumException errorEnum, Map<String, String> context) {
        super(message);
        this.errorEnum = errorEnum;
        this.context = context;
    }

    public ServiceException(String message, Throwable cause, EnumException errorEnum, Map<String, String> context) {
        super(message, cause);
        this.errorEnum = errorEnum;
        this.context = context;
    }

    public ServiceException(Throwable cause, EnumException errorEnum, Map<String, String> context) {
        super(cause);
        this.errorEnum = errorEnum;
        this.context = context;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, EnumException errorEnum, Map<String, String> context) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorEnum = errorEnum;
        this.context = context;
    }

    public EnumException getErrorEnum() {
        return errorEnum;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }
}
