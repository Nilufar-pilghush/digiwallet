package ir.pilqush.ewallet.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Data
@Entity
@Table
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = WalletEntity.class)
    @JoinColumn
    private WalletEntity sender;


    @ManyToOne(targetEntity = WalletEntity.class)
    @JoinColumn
    private WalletEntity recipient;


    @Column(columnDefinition = "numeric")
    private BigDecimal amount;

    private String transactionRef;
    @Column(columnDefinition = "INTEGER")
    @Enumerated
    private TransactionStatus status;
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

}