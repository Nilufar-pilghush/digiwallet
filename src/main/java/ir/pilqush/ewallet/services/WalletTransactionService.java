package ir.pilqush.ewallet.services;

import ir.pilqush.ewallet.dto.WalletRequest;
import ir.pilqush.ewallet.entities.TransactionStatus;
import ir.pilqush.ewallet.entities.WalletEntity;
import ir.pilqush.ewallet.entities.WalletTransactionEntity;
import ir.pilqush.ewallet.exceptions.BadRequestException;
import ir.pilqush.ewallet.exceptions.EnumException;
import ir.pilqush.ewallet.exceptions.NotFoundException;
import ir.pilqush.ewallet.exceptions.TransactionException;
import ir.pilqush.ewallet.repositories.WalletRepository;
import ir.pilqush.ewallet.repositories.WalletTransactionRepository;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class WalletTransactionService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public WalletTransactionService(WalletRepository walletRepository,
                                    WalletTransactionRepository walletTransactionRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }


    @Transactional(rollbackOn = Exception.class)
    public WalletEntity createWallet(final WalletRequest walletRequest) {
        if (walletRepository.findByEmail(walletRequest.getEmail()).isPresent()) {
            throw new BadRequestException(EnumException.WALLET_EXIST);
        }
        WalletEntity wallet = WalletEntity.builder()
                .balance(Optional.ofNullable(walletRequest.getBalance()).orElse(BigDecimal.ZERO))
                .email(walletRequest.getEmail())
                .name(walletRequest.getName())
                .build();
        walletRepository.persist(wallet);
        return walletRepository.findByEmail(walletRequest.getEmail())
                .orElseThrow(() -> new TransactionException(EnumException.WALLET_CREATION_FAILED));
    }

    public WalletEntity getWallet(long walletId) {
        return walletRepository.getById(walletId)
                .orElseThrow(() -> new NotFoundException("WALLET_NOT_FOUND"));
    }

    @Transactional(rollbackOn = Exception.class)
    public WalletTransactionEntity transaction(final long senderId, final long recipientId,
                                               final BigDecimal amount) {
        WalletEntity sender = walletRepository.getById(senderId)
                .orElseThrow(() -> new NotFoundException("WALLET_NOT_FOUND"));
        WalletEntity recipient = walletRepository.getById(recipientId)
                .orElseThrow(() -> new NotFoundException("WALLET_NOT_FOUND"));
        try {
            sender.debit(amount);
            recipient.credit(amount);
            walletRepository.persist(sender, recipient);
            WalletTransactionEntity walletTransaction = createWalletTransaction(amount, sender, recipient,
                    TransactionStatus.COMPLETED);
            walletTransactionRepository.persist(walletTransaction);
            return walletTransaction;
        } catch (TransactionException e) {
            Map<String, String> context = updateFailedTransaction(sender, recipient, amount,
                    e.getContext());
            throw new TransactionException(e.getErrorEnum(), context, e);
        } catch (Exception e) {
            Map<String, String> context = updateFailedTransaction(sender, recipient, amount,
                    new HashMap<>());
            throw new TransactionException(EnumException.UNKNOWN, context, e);
        }
    }

    private WalletTransactionEntity createWalletTransaction(BigDecimal amount, WalletEntity sender,
                                                            WalletEntity recipient, TransactionStatus status) {
        return WalletTransactionEntity.builder()
                .amount(amount)
                .sender(sender)
                .recipient(recipient)
                .status(status)
                .build();
    }


    private Map<String, String> updateFailedTransaction(WalletEntity sender, WalletEntity recipient,
                                                        BigDecimal amount, Map<String, String> context) {
        WalletTransactionEntity walletTransaction = createWalletTransaction(amount, sender, recipient,
                TransactionStatus.FAILED);
        context.put("transactionStatus", walletTransaction.getStatus().name());
        return context;
    }

}
