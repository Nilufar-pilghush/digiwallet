package ir.pilqush.ewallet.dto;

import ir.pilqush.ewallet.entities.TransactionStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class WalletTransactionRequest {
    @NotNull(message = "wallet cannot be null")
    @Min(value = 0, message = "amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "recipientId cannot be null")
    @Min(value = 1, message = "recipientId must be greater than 0")
    private Long recipientWalletId;
    private TransactionStatus status;

    public WalletTransactionRequest(BigDecimal amount, Long recipientWalletId, TransactionStatus status) {
        this.amount = amount;
        this.recipientWalletId = recipientWalletId;
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getRecipientWalletId() {
        return recipientWalletId;
    }

    public void setRecipientWalletId(Long recipientWalletId) {
        this.recipientWalletId = recipientWalletId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
