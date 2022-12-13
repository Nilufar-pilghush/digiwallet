package ir.pilqush.ewallet.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ir.pilqush.ewallet.entities.WalletTransactionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WalletTransactionRepository implements PanacheRepository<WalletTransactionEntity> {

}