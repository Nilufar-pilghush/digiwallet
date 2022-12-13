package ir.pilqush.ewallet.facade;

import ir.pilqush.ewallet.dto.*;

import ir.pilqush.ewallet.entities.WalletTransactionEntity;
import ir.pilqush.ewallet.exceptions.BadRequestException;
import ir.pilqush.ewallet.exceptions.EnumException;
import ir.pilqush.ewallet.services.WalletTransactionService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@Slf4j
public class WalletServiceFacade {

    private final WalletTransactionService walletTransactionService;

    public WalletServiceFacade(WalletTransactionService walletTransactionService) {
        this.walletTransactionService = walletTransactionService;
    }

    public CompletionStage<WalletResponse> createWallet(WalletRequest walletRequest) {
        return CompletableFuture.supplyAsync(
                        () -> walletTransactionService.createWallet(walletRequest))
                .thenApply(res -> WalletResponse.builder()
                        .walletId(res.getId())
                        .balance(res.getBalance())
                        .name(res.getName())
                        .email(res.getEmail())
                        .createdAt(res.getCreatedAt().getTime())
                        .build());
    }

    public CompletionStage<BalanceResponse> getBalance(long walletId) {
        return CompletableFuture.supplyAsync(
                        () -> {
                                return walletTransactionService.getWallet(walletId);
                            }
                        )
                .thenApply(res -> BalanceResponse.builder().amount(res.getBalance()).build());
    }

    public CompletableFuture<WalletTransactionEntity> transaction(long walletId,
                                                                  WalletTransactionRequest transactionRequest) {
        if (transactionRequest.getRecipientWalletId().equals(walletId)) {
            throw new BadRequestException(EnumException.SENDER_RECIPIENT_SAME);
        }
        return CompletableFuture.supplyAsync(() -> {
                    return walletTransactionService
                            .transaction(walletId, transactionRequest.getRecipientWalletId(),
                                    transactionRequest.getAmount());
        });
    }
}