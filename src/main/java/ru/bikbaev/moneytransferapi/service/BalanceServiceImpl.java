package ru.bikbaev.moneytransferapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.moneytransferapi.core.entity.Account;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.core.validation.BalanceValidator;
import ru.bikbaev.moneytransferapi.core.exception.AccountNotFoundException;
import ru.bikbaev.moneytransferapi.repository.AccountRepository;
import ru.bikbaev.moneytransferapi.security.JwtService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {
    private final JwtService jwtService;
    private final AccountRepository repository;
    private final BalanceValidator validator;


    private final BigDecimal MAX_BALANCE_INCREASE_PERCENT = BigDecimal.valueOf(3.07);
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
        int pageNumber = 0;
        Page<Account> page;

        do {
            page = repository.findAllWithLock(PageRequest.of(pageNumber, PAGE_SIZE));

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
            pageNumber++;

        } while (!page.isLast());
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void transferMoney(String token, Long toUserId, BigDecimal amount) {

        Long fromUserId = jwtService.extractUserId(token);

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

    }

    private Account findByIdUser(Long id) {
        return repository.findByIdUserWithLock(id).orElseThrow(
                () -> new AccountNotFoundException("Account not found")
        );
    }
}
