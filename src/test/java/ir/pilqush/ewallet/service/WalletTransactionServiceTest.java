package ir.pilqush.ewallet.service;

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
import ir.pilqush.ewallet.services.WalletTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class WalletTransactionServiceTest {

    @Mock
    WalletRepository walletRepository;
    @Mock
    WalletTransactionRepository walletTransactionRepository;
    @InjectMocks
    WalletTransactionService walletTransactionService;

    @Test
    public void shouldCreditTransactionMoneyWhenSenderHasSufficientMoney() {
        doNothing().when(walletRepository)
                .persist(any(WalletEntity.class), any(WalletEntity.class));
        doNothing().when(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        Optional<WalletEntity> sender = Optional
                .of(WalletEntity.builder().id(1L).balance(BigDecimal.valueOf(50)).build());
        Optional<WalletEntity> recipient = Optional
                .of(WalletEntity.builder().id(2L).balance(BigDecimal.valueOf(10)).build());
        doReturn(sender).when(walletRepository).getById(eq(1L));
        doReturn(recipient).when(walletRepository).getById(eq(2L));
        WalletTransactionEntity walletTransaction = walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(20));
        assertThat(walletTransaction).isNotNull();
        assertThat(walletTransaction.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
        verify(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).persist(any(WalletEntity.class), any(WalletEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository).getById(eq(2L));
    }

    @Test
    public void shouldDebitTransactionMoneyWhenSenderHasSufficientMoney() {
        doNothing().when(walletRepository)
                .persist(any(WalletEntity.class), any(WalletEntity.class));
        doNothing().when(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        Optional<WalletEntity> sender = Optional
                .of(WalletEntity.builder().id(1L).balance(BigDecimal.valueOf(50)).build());
        Optional<WalletEntity> recipient = Optional
                .of(WalletEntity.builder().id(2L).balance(BigDecimal.valueOf(10)).build());
        doReturn(sender).when(walletRepository).getById(eq(1L));
        doReturn(recipient).when(walletRepository).getById(eq(2L));
        WalletTransactionEntity walletTransaction = walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(-10));
        assertThat(walletTransaction).isNotNull();
        assertThat(walletTransaction.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
        verify(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).persist(any(WalletEntity.class), any(WalletEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository).getById(eq(2L));
    }


    @Test
    public void shouldThrowExceptionWhenSenderHasInSufficientMoney() {
        doNothing().when(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        Optional<WalletEntity> sender = Optional
                .of(WalletEntity.builder().id(1L).balance(BigDecimal.valueOf(10)).build());
        Optional<WalletEntity> recipient = Optional
                .of(WalletEntity.builder().id(2L).balance(BigDecimal.valueOf(10)).build());
        doReturn(sender).when(walletRepository).getById(eq(1L));
        doReturn(recipient).when(walletRepository).getById(eq(2L));
        assertThatExceptionOfType(TransactionException.class)
                .isThrownBy(() -> walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(20)))
                .matches((e) -> e.getErrorEnum().getMessage().equals("Wallet has insufficient balance"));
        verify(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository).getById(eq(2L));
        verify(walletRepository, never()).persist(any(WalletEntity.class), any(WalletEntity.class));
    }

    @Test
    public void shouldThrowExceptionWhenSenderDoesNotExist() {
        doReturn(Optional.empty()).when(walletRepository).getById(eq(1L));
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(20)))
                .matches((e) -> e.getErrorEnum().getMessage()
                        .equals("Wallet (sender/recipient) doesn't exist"));
        verify(walletTransactionRepository, never()).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository, never()).getById(eq(2L));
        verify(walletRepository, never()).persist(any(WalletEntity.class), any(WalletEntity.class));
    }

    @Test
    public void shouldThrowExceptionWhenRecipientDoesNotExist() {
        Optional<WalletEntity> sender = Optional
                .of(WalletEntity.builder().id(1L).balance(BigDecimal.valueOf(10)).build());
        doReturn(sender).when(walletRepository).getById(eq(1L));
        doReturn(Optional.empty()).when(walletRepository).getById(eq(2L));
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(20)))
                .matches((e) -> e.getErrorEnum().getMessage()
                        .equals("Wallet (sender/recipient) doesn't exist"));
        verify(walletTransactionRepository, never()).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository).getById(eq(2L));
        verify(walletRepository, never()).persist(any(WalletEntity.class), any(WalletEntity.class));
    }

    @Test
    public void shouldThrowExceptionWhenDBExceptionOccur() {
        doThrow(RuntimeException.class).when(walletRepository)
                .persist(any(WalletEntity.class), any(WalletEntity.class));
        doNothing().when(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        Optional<WalletEntity> sender = Optional
                .of(WalletEntity.builder().id(1L).balance(BigDecimal.valueOf(50)).build());
        Optional<WalletEntity> recipient = Optional
                .of(WalletEntity.builder().id(2L).balance(BigDecimal.valueOf(10)).build());
        doReturn(sender).when(walletRepository).getById(eq(1L));
        doReturn(recipient).when(walletRepository).getById(eq(2L));
        assertThatExceptionOfType(TransactionException.class)
                .isThrownBy(() -> walletTransactionService.transaction(1L, 2L, BigDecimal.valueOf(20)))
                .matches((e) -> e.getErrorEnum().getMessage().equals("Unknown error occurred"));
        verify(walletTransactionRepository).persist(any(WalletTransactionEntity.class));
        verify(walletRepository).getById(eq(1L));
        verify(walletRepository).getById(eq(2L));
        verify(walletRepository).persist(any(WalletEntity.class), any(WalletEntity.class));
    }

    @Test
    public void shouldCreateWalletWhenSuccess() {
        WalletEntity expected = WalletEntity.builder().id(1L).email("test@gmail.com").build();
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        doNothing().when(walletRepository).persist(any(WalletEntity.class));
        when(walletRepository.findByEmail(anyString())).thenReturn(Optional.empty())
                .thenReturn(Optional.of(expected));
        WalletEntity actual = walletTransactionService.createWallet(walletRequest);
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isGreaterThanOrEqualTo(1L);
        assertThat(actual.getEmail()).isEqualTo("test@gmail.com");
        verify(walletRepository, times(2)).findByEmail(anyString());
        verify(walletRepository).persist(any(WalletEntity.class));
    }

    @Test
    public void shouldNotCreateWalletWhenWalletWithSameEmailExist() {
        WalletEntity expected = WalletEntity.builder().id(1L).email("test@gmail.com").build();
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        when(walletRepository.findByEmail(anyString())).thenReturn(Optional.of(expected));
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> walletTransactionService.createWallet(walletRequest))
                .matches(ex -> ex.getErrorEnum() == EnumException.WALLET_EXIST);
        verify(walletRepository).findByEmail(anyString());
        verify(walletRepository, never()).persist(any(WalletEntity.class));
    }

    @Test
    public void shouldNotCreateWalletWhenDbExceptionThrown() {
        doThrow(RuntimeException.class).when(walletRepository).persist(any(WalletEntity.class));
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        when(walletRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> walletTransactionService.createWallet(walletRequest));
        verify(walletRepository).findByEmail(anyString());
        verify(walletRepository).persist(any(WalletEntity.class));
    }

    @Test
    public void shouldNotCreateWalletForUnknownReason() {
        WalletRequest walletRequest = new WalletRequest("test", "test@gmail.com", BigDecimal.TEN);
        doNothing().when(walletRepository).persist(any(WalletEntity.class));
        when(walletRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThatExceptionOfType(TransactionException.class)
                .isThrownBy(() -> walletTransactionService.createWallet(walletRequest))
                .matches(ex -> ex.getErrorEnum() == EnumException.WALLET_CREATION_FAILED);
        verify(walletRepository, times(2)).findByEmail(anyString());
        verify(walletRepository).persist(any(WalletEntity.class));
    }

    @Test
    public void shouldGetWalletSuccess() {
        WalletEntity expected = WalletEntity.builder().id(1L).email("test@gmail.com").balance(BigDecimal.TEN)
                .build();
        when(walletRepository.getById(eq(1L))).thenReturn(Optional.of(expected));
        WalletEntity actual = walletTransactionService.getWallet(1L);
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo("test@gmail.com");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getBalance()).isEqualTo(BigDecimal.TEN);
        verify(walletRepository).getById(1L);
    }

    @Test
    public void shouldGetWalletFailureWhenWalletDoesNotExist() {
        when(walletRepository.getById(eq(1L))).thenReturn(Optional.empty());
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> walletTransactionService.getWallet(1L))
                .matches(ex -> ex.getErrorEnum() == EnumException.WALLET_NOT_FOUND);
        verify(walletRepository).getById(1L);
    }
}