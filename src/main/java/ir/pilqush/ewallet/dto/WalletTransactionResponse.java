package ir.pilqush.ewallet.dto;

import ir.pilqush.ewallet.entities.TransactionStatus;

public class WalletTransactionResponse {
    public static WalletTransactionRequest transaction;
    private String transactionReference;
    private TransactionStatus status;

    public WalletTransactionResponse(String transactionReference, TransactionStatus status) {
        this.transactionReference = transactionReference;
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
