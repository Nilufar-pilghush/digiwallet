package ir.pilqush.ewallet.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;

@Getter

public enum EnumException {

    WALLET_NOT_FOUND(404, "SE-4004", "Wallet (sender/recipient) doesn't exist"),
    INSUFFICIENT_BALANCE(400, "SE-4005", "Wallet has insufficient balance"),
    UNKNOWN(500, "SE-5001", "Unknown error occurred"),
    WALLET_EXIST(400, "SE-4006", "Wallet with same email already exist"),
    WALLET_CREATION_FAILED(500, "SE-4007",
            "Due to unkown issue wallet creation failed, please try again"),
    SENDER_RECIPIENT_SAME(400, "SE-4007", "Sender and recipient cannot be same");
    private final int code;
    private final String serviceCode;
    private final String message;

    EnumException(int code, String serviceCode, String message) {
        this.code = code;
        this.serviceCode = serviceCode;
        this.message = message;
    }

}