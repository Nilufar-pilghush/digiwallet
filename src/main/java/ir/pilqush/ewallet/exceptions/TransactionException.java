package ir.pilqush.ewallet.exceptions;

import java.util.Map;

public class TransactionException extends ServiceException {

    public TransactionException(EnumException enumException, Map<String, String> context, Throwable ex) {
        super(enumException, context, ex);
    }

    public TransactionException(EnumException insufficientBalance) {
        super(insufficientBalance);
    }
}