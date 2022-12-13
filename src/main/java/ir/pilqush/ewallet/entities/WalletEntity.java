package ir.pilqush.ewallet.entities;

import ir.pilqush.ewallet.base.BaseEntity;
import ir.pilqush.ewallet.exceptions.EnumException;
import ir.pilqush.ewallet.exceptions.TransactionException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Data
@Entity
@DynamicUpdate

public class WalletEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(columnDefinition = "numeric")
    private BigDecimal balance;

    @Column(columnDefinition = "timestamp")
    private Timestamp createdAt;

    @Column(columnDefinition = "timestamp")
    private Timestamp updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = Optional.ofNullable(createdAt).orElse(new Timestamp(Instant.now().toEpochMilli()));
        updatedAt = Optional.ofNullable(updatedAt).orElse(new Timestamp(Instant.now().toEpochMilli()));
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Optional.ofNullable(updatedAt).orElse(new Timestamp(Instant.now().toEpochMilli()));
    }

    public WalletEntity(Long id, String name, String email, BigDecimal balance, Timestamp createdAt, Timestamp updatedAt) {

    }

    public WalletEntity() {

    }

    public static WalletBuilder builder() {

        return new WalletBuilder();
    }

    public void credit(BigDecimal amount) {
        balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO);
        if (amount.compareTo(BigDecimal.ZERO) < 0 && balance.compareTo(amount.abs()) < 0) {
            throw new TransactionException(EnumException.INSUFFICIENT_BALANCE);
        }
        balance = balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        balance = Optional.ofNullable(balance).orElse(BigDecimal.ZERO);
        if (amount.compareTo(BigDecimal.ZERO) > 0 && balance.compareTo(amount) < 0) {
            throw new TransactionException(EnumException.INSUFFICIENT_BALANCE);
        }
        balance = balance.subtract(amount);
    }

    public static class WalletBuilder {
        private Long id;
        private String name;
        private String email;
        private BigDecimal balance;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        WalletBuilder() {
        }

        public WalletBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WalletBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WalletBuilder email(String email) {
            this.email = email;
            return this;
        }

        public WalletBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public WalletBuilder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public WalletBuilder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public WalletEntity build() {
            return new WalletEntity(id, name, email, balance, createdAt, updatedAt);
        }

        public String toString() {
            return "WalletEntity.WalletBuilder(" +
                    "id=" + this.id +
                    ", name=" + this.name +
                    ", email=" + this.email + ", " +
                    "balance=" + this.balance + ", " +
                    "createdAt=" + this.createdAt + ", " +
                    "updatedAt=" + this.updatedAt + ")";
        }
    }
}
