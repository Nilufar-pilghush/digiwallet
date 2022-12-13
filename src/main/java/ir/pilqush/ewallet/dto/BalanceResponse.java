package ir.pilqush.ewallet.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;


@Builder
public class BalanceResponse {
    private BigDecimal amount;

    public BalanceResponse(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
