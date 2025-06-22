package ru.bikbaev.moneytransferapi.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.bikbaev.moneytransferapi.core.entity.Account;
import ru.bikbaev.moneytransferapi.core.entity.User;
import ru.bikbaev.moneytransferapi.core.exception.AccountNotFoundException;
import ru.bikbaev.moneytransferapi.core.exception.AmountMustBePositiveException;
import ru.bikbaev.moneytransferapi.core.exception.InsufficientFundsException;
import ru.bikbaev.moneytransferapi.core.exception.TransferToSelfException;
import ru.bikbaev.moneytransferapi.core.validation.BalanceValidator;
import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;
import ru.bikbaev.moneytransferapi.repository.AccountRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;
import ru.bikbaev.moneytransferapi.service.BalanceServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AccountRepository repository;

    @Mock
    private BalanceValidator validator;

    @InjectMocks
    private BalanceServiceImpl balanceService;


    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(1.1);
    private final int PAGE_SIZE = 10;


    String userName = "Test User";
    BigDecimal balance = BigDecimal.valueOf(2000);
    BigDecimal initialBalance = BigDecimal.valueOf(2000);

    List<Account> createAccountsInRange(int start, int end) {
        List<Account> accounts = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            accounts.add(createTestAccount(i));
        }
        return accounts;
    }

    Account createTestAccount(int i) {
        User testUser = createTestUser(i);
        return Account.builder().id((long) i).user(testUser).balance(balance).initialBalance(initialBalance).build();
    }

    User createTestUser(int i) {
        return User.builder().id((long) i).name(userName + " " + i).build();
    }


    @Test
    void accruePercentageToBalances_shouldIncreaseBalanceBelowMax() {
        List<Account> accounts = createAccountsInRange(0, 10);

        Page<Account> page = new PageImpl<>(accounts, PageRequest.of(0, PAGE_SIZE), 10);

        when(repository.findAllWithLock(PageRequest.of(0, PAGE_SIZE))).thenReturn(page);

        balanceService.accruePercentageToBalances();

        for (Account account : accounts) {
            BigDecimal expected = initialBalance.multiply(INTEREST_RATE);
            assertEquals(0, account.getBalance().compareTo(expected));
        }

        verify(repository).findAllWithLock(any());
        verify(repository).saveAll(any());
    }


    @Test
    void transferMoneyTest() {

        String token = "token";
        TransferMoneyRequest request = new TransferMoneyRequest(2L, BigDecimal.valueOf(100));

        BigDecimal balanceAccount = BigDecimal.valueOf(10000);

        BigDecimal amount = request.getAmount();

        Long fromUserId = 1L;
        Long toUserId = request.getToUserId();

        User fromUser = User.builder().id(fromUserId).name("formUser").build();
        User toUser = User.builder().id(toUserId).name("toUser").build();

        Account fromUserAccount = Account.builder().balance(balanceAccount).user(fromUser).build();
        Account toUserAccount = Account.builder().balance(balanceAccount).user(toUser).build();

        when(jwtService.extractUserId(token)).thenReturn(1L);
        when(repository.findByIdUserWithLock(fromUserId)).thenReturn(Optional.of(fromUserAccount));
        when(repository.findByIdUserWithLock(toUserId)).thenReturn(Optional.of(toUserAccount));

        TransferMoneyResponse result = balanceService.transferMoney(token, request);

        assertNotNull(result);
        assertEquals(fromUser.getName(), result.getFromUserName());
        assertEquals(toUser.getName(), result.getToUserName());
        assertEquals(amount, result.getAmountTransfer());
        assertEquals(balanceAccount.subtract(amount), result.getCurrentBalance());

        assertEquals(balanceAccount.subtract(amount), fromUserAccount.getBalance());
        assertEquals(balanceAccount.add(amount), toUserAccount.getBalance());

        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator).validateAmountTransfer(amount);
        verify(validator).validateBalanceForTransfer(amount, balanceAccount);
        verify(repository).saveAll(List.of(fromUserAccount, toUserAccount));
    }


    @Test
    void transferMoneyTest_validateTransferToSerf() {
        TransferMoneyRequest request = new TransferMoneyRequest(1L, BigDecimal.valueOf(100));
        String token = "token";
        Long fromUserId = 1L;
        Long toUserId = 1L;

        when(jwtService.extractUserId(token)).thenReturn(fromUserId);

        doThrow(new TransferToSelfException("Cannot transfer money to the same account."))
                .when(validator).validateTransferToSelf(fromUserId, toUserId);

        assertThrows(TransferToSelfException.class,
                () -> balanceService.transferMoney(token, request));

        verify(jwtService).extractUserId(token);
        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator, never()).validateAmountTransfer(any());
        verify(repository, never()).findByIdUserWithLock(any());
        verify(validator, never()).validateBalanceForTransfer(any(), any());
        verify(repository, never()).saveAll(any());
    }


    @Test
    void transferMoneyTest_validateAmountTransfer() {
        TransferMoneyRequest request = new TransferMoneyRequest(1L, BigDecimal.valueOf(-100));
        String token = "token";
        Long fromUserId = 1L;
        Long toUserId = 1L;

        when(jwtService.extractUserId(token)).thenReturn(fromUserId);

        doThrow(new AmountMustBePositiveException("Transfer amount must be greater than zero."))
                .when(validator).validateAmountTransfer(request.getAmount());

        assertThrows(AmountMustBePositiveException.class,
                () -> balanceService.transferMoney(token, request));


        verify(jwtService).extractUserId(token);
        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator).validateAmountTransfer(any());
        verify(repository, never()).findByIdUserWithLock(any());
        verify(validator, never()).validateBalanceForTransfer(any(), any());
        verify(repository, never()).saveAll(any());

    }


    @Test
    void transferMoneyTest_accountFromTransferNotFound() {
        TransferMoneyRequest request = new TransferMoneyRequest(2L, BigDecimal.valueOf(100));
        String token = "token";
        Long fromUserId = 1L;
        Long toUserId = request.getToUserId();

        when(jwtService.extractUserId(token)).thenReturn(fromUserId);
        when(repository.findByIdUserWithLock(fromUserId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> balanceService.transferMoney(token, request));

        verify(jwtService).extractUserId(token);
        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator).validateAmountTransfer(any());
        verify(repository, times(1)).findByIdUserWithLock(any());
        verify(validator, never()).validateBalanceForTransfer(any(), any());
        verify(repository, never()).saveAll(any());

    }


    @Test
    void transferMoneyTest_accountToTransferNotFound() {
        TransferMoneyRequest request = new TransferMoneyRequest(2L, BigDecimal.valueOf(100));
        String token = "token";
        Long fromUserId = 1L;
        Long toUserId = request.getToUserId();

        Account fromUserAccount = Account.builder().build();


        when(jwtService.extractUserId(token)).thenReturn(fromUserId);
        when(repository.findByIdUserWithLock(fromUserId)).thenReturn(Optional.of(fromUserAccount));
        when(repository.findByIdUserWithLock(toUserId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> balanceService.transferMoney(token, request));

        verify(jwtService).extractUserId(token);
        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator).validateAmountTransfer(any());
        verify(repository, times(2)).findByIdUserWithLock(any());
        verify(validator, never()).validateBalanceForTransfer(any(), any());
        verify(repository, never()).saveAll(any());

    }


    @Test
    void transferMoneyTest_validateBalanceTransfer() {
        TransferMoneyRequest request = new TransferMoneyRequest(2L, BigDecimal.valueOf(100));
        String token = "token";
        Long fromUserId = 1L;
        Long toUserId = request.getToUserId();

        BigDecimal balanceFromUser = BigDecimal.valueOf(50);
        Account fromUserAccount = Account.builder().balance(balanceFromUser).build();

        Account toUserAccount = Account.builder().build();



        when(jwtService.extractUserId(token)).thenReturn(fromUserId);
        when(repository.findByIdUserWithLock(fromUserId)).thenReturn(Optional.of(fromUserAccount));
        when(repository.findByIdUserWithLock(toUserId)).thenReturn(Optional.of(toUserAccount));
        doThrow(new InsufficientFundsException("Transfer amount must be greater than zero."))
                .when(validator).validateBalanceForTransfer(request.getAmount(),balanceFromUser);

        assertThrows(InsufficientFundsException.class,
                ()->balanceService.transferMoney(token,request));

        verify(jwtService).extractUserId(token);
        verify(validator).validateTransferToSelf(fromUserId, toUserId);
        verify(validator).validateAmountTransfer(any());
        verify(repository, times(2)).findByIdUserWithLock(any());
        verify(validator).validateBalanceForTransfer(request.getAmount(),balanceFromUser);
        verify(repository, never()).saveAll(any());

    }

}
