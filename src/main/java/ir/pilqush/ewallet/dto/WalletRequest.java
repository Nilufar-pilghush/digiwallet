package ir.pilqush.ewallet.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class WalletRequest {
    @NotNull
    private String name;
    @Email(message = "invalid email address")
    private String email;
    @Min(value = 0, message = "initial balance must be greater or equal to zero")
    private BigDecimal balance;

    public WalletRequest(String test, String s, BigDecimal ten) {

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
}
