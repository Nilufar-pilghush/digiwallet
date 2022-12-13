package ir.pilqush.ewallet.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import ir.pilqush.ewallet.entities.WalletEntity;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class WalletRepository implements PanacheRepository<WalletEntity> {

    @Transactional
    public Optional<WalletEntity> getById(long id) {
        return Optional.ofNullable(findById(id));
    }

    @Transactional
    public Optional<WalletEntity> findByEmail(String email) {
        WalletEntity walletEntity = find("SELECT a from Account a where a.email = :em",
                Parameters.with("em", email).map()).firstResult();
        return Optional.ofNullable(walletEntity);
    }
}
