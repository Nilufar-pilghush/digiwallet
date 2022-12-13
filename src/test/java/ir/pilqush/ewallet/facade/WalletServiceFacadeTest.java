package ir.pilqush.ewallet.facade;


import ir.pilqush.ewallet.dto.*;
import ir.pilqush.ewallet.entities.WalletEntity;
import ir.pilqush.ewallet.entities.WalletTransactionEntity;
import ir.pilqush.ewallet.exceptions.NotFoundException;
import ir.pilqush.ewallet.exceptions.TransactionException;
import ir.pilqush.ewallet.services.WalletTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
//import java.util.UUID;
import java.util.concurrent.CompletionException;

import static ir.pilqush.ewallet.entities.TransactionStatus.COMPLETED;
import static ir.pilqush.ewallet.entities.TransactionStatus.FAILED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class WalletServiceFacadeTest {

    @Mock
    WalletTransactionService walletTransactionService;

    @InjectMocks
    WalletServiceFacade walletServiceFacade;

    @Test
    public void shouldCreateWalletSuccess() {
        WalletEntity expected = WalletEntity.builder().id(1L).email("test@gmail.com")
                .createdAt(new Timestamp(Instant.now().toEpochMilli())).build();
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        doReturn(expected).when(walletTransactionService).createWallet(any(WalletRequest.class));
        WalletResponse actual = walletServiceFacade.createWallet(walletRequest)
                .toCompletableFuture().join();
        assertThat(actual).isNotNull();
        assertThat(actual.getWalletId()).isEqualTo(1L);
        assertThat(actual.getEmail()).isEqualTo("test@gmail.com");
        verify(walletTransactionService).createWallet(any(WalletRequest.class));
    }

    @Test
    public void shouldCreateWalletFailureWhenWalletServiceThrowsException() {
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        doThrow(TransactionException.class).when(walletTransactionService)
                .createWallet(any(WalletRequest.class));
        assertThatExceptionOfType(CompletionException.class).isThrownBy(
                        () -> walletServiceFacade.createWallet(walletRequest).toCompletableFuture().join())
                .withCauseInstanceOf(TransactionException.class);
        verify(walletTransactionService).createWallet(any(WalletRequest.class));
    }

    @Test
    public void shouldGetBalanceSuccess() {
        WalletEntity expected = WalletEntity.builder().id(1L).email("test@gmail.com").balance(BigDecimal.TEN)
                .createdAt(new Timestamp(Instant.now().toEpochMilli())).build();
        when(walletTransactionService.getWallet(eq(1L))).thenReturn(expected);
    }

    @Test
    public void shouldGetBalanceFailureWhenWalletServiceThrowsException() {
        when(walletTransactionService.getWallet(eq(1L))).thenThrow(NotFoundException.class);
        assertThatExceptionOfType(CompletionException.class);

    }

    @Test
    public void shouldTransactionMoneySuccess() {
        WalletTransactionRequest transactionRequest = new WalletTransactionRequest(BigDecimal.ONE, 2L,COMPLETED);
        WalletTransactionEntity walletTransaction = WalletTransactionEntity.builder()
                .status(COMPLETED)
                .build();
        when(walletTransactionService.transaction(eq(1L), eq(2L), eq(BigDecimal.ONE))).thenReturn(walletTransaction);
        verify(walletTransactionService).transaction(eq(1L), eq(2L), eq(BigDecimal.ONE));

    }

    @Test
    public void shouldTransactionMoneyFailureWhenWalletServiceThrowsException() {
        WalletTransactionRequest transactionRequest = new WalletTransactionRequest(BigDecimal.ONE,2L,FAILED);
        when(walletTransactionService.transaction(eq(1L), eq(2L), eq(BigDecimal.ONE)))
                .thenThrow(TransactionException.class);
        assertThatExceptionOfType(CompletionException.class).isThrownBy(
                        () -> walletServiceFacade.transaction(1L, transactionRequest).toCompletableFuture().join())
                .withCauseInstanceOf(TransactionException.class);
        verify(walletTransactionService).transaction(eq(1L), eq(2L), eq(BigDecimal.ONE));

    }
}



