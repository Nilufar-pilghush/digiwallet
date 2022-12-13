package ir.pilqush.ewallet.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class WalletResponse {
    private Long walletId;
    private String name;
    private String email;
    private BigDecimal balance;
    private Long createdAt;

    public WalletResponse(Long walletId,
                          String name,
                          String email,
                          BigDecimal balance,
                          Long createdAt) {
        this.walletId = walletId;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
