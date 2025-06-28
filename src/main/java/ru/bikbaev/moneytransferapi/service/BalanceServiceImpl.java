package ru.bikbaev.moneytransferapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.moneytransferapi.core.entity.Account;
import ru.bikbaev.moneytransferapi.core.exception.AccountNotFoundException;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.core.validation.BalanceValidator;
import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserBalanceResponse;
import ru.bikbaev.moneytransferapi.repository.AccountRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BalanceServiceImpl implements BalanceService {
    private final JwtService jwtService;
    private final AccountRepository repository;
    private final BalanceValidator validator;


    private final BigDecimal MAX_BALANCE_INCREASE_PERCENT = BigDecimal.valueOf(2.07);
    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(1.1);
    private final int PAGE_SIZE = 10;


    public BalanceServiceImpl(JwtService jwtService, AccountRepository repository, BalanceValidator validator) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void accruePercentageToBalances() {
        log.info("Starting accrual of interest to account balances");
        int pageNumber = 0;
        Page<Account> page;
        int totalUpdatedAccounts = 0;

        try {
            do {
                page = repository.findAllWithLock(PageRequest.of(pageNumber, PAGE_SIZE));
                log.debug("Processing page {} with {} accounts", pageNumber, page.getNumberOfElements());

                List<Account> accountsToUpdate = new ArrayList<>();

                for (Account account : page.getContent()) {
                    BigDecimal initial = account.getInitialBalance();
                    BigDecimal current = account.getBalance();
                    BigDecimal max = initial.multiply(MAX_BALANCE_INCREASE_PERCENT);
                    BigDecimal increased = current.multiply(INTEREST_RATE);

                    if (current.compareTo(max) < 0) {
                        account.setBalance(increased.min(max));
                        accountsToUpdate.add(account);
                    }
                }

                repository.saveAll(accountsToUpdate);
                log.debug("Updated {} accounts on page {}", accountsToUpdate.size(), pageNumber);
                totalUpdatedAccounts += accountsToUpdate.size();
                pageNumber++;

            } while (!page.isLast());

            log.info("Finished accrual, total updated accounts: {}", totalUpdatedAccounts);

        } catch (Exception ex) {
            log.error("Failed to accrue interest to balances: {}", ex.getMessage(), ex);
            throw ex;
        }
    }



    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public TransferMoneyResponse transferMoney(String token, TransferMoneyRequest request) {
            Long fromUserId = jwtService.extractUserId(token);
            log.info("Start money transfer from_user_id={} to_user_id={} amount={}", fromUserId, request.getToUserId(), request.getAmount());

            Long toUserId = request.getToUserId();
            BigDecimal amount = request.getAmount();

            validator.validateTransferToSelf(fromUserId, toUserId);
            validator.validateAmountTransfer(amount);

            Account accountFromTransfer = findByIdUser(fromUserId);
            Account accountToTransfer = findByIdUser(toUserId);

            BigDecimal fromTransferBalance = accountFromTransfer.getBalance();
            BigDecimal toTransferBalance = accountToTransfer.getBalance();

            validator.validateBalanceForTransfer(amount, fromTransferBalance);

            fromTransferBalance = fromTransferBalance.subtract(amount);
            toTransferBalance = toTransferBalance.add(amount);

            accountFromTransfer.setBalance(fromTransferBalance);
            accountToTransfer.setBalance(toTransferBalance);


            repository.saveAll(List.of(accountToTransfer, accountFromTransfer));
            log.info("Transfer successful: from_user_id={} to_user_id={} amount={}", fromUserId, toUserId, amount);

            return TransferMoneyResponse.builder()
                    .fromUserName(accountFromTransfer.getUser().getName())
                    .toUserName(accountToTransfer.getUser().getName())
                    .amountTransfer(amount)
                    .currentBalance(accountFromTransfer.getBalance())
                    .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
    }

    @Override
    public UserBalanceResponse getUserBalance(String token) {
        Long userId = jwtService.extractUserId(token);
        log.info("Search user_account by user_id={}",userId);

        Account userAccount = repository.findByIdUserFetch(userId).orElseThrow(
                () -> new AccountNotFoundException("Account not found")
        );
        return new UserBalanceResponse(userAccount.getUser().getName(), userAccount.getBalance());
    }

    private Account findByIdUser(Long id) {
        log.info("With lock search user_account by user_id={}",id);
        return repository.findByIdUserWithLock(id).orElseThrow(
                () -> new AccountNotFoundException("Account not found")
        );
    }
}
