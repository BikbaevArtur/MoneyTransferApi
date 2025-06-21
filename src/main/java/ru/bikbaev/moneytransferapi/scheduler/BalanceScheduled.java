package ru.bikbaev.moneytransferapi.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;

@Component
public class BalanceScheduled {
    private final BalanceService balanceService;

    public BalanceScheduled(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Scheduled(fixedRate = 30000)
    public void accruePercentageToBalances() {
        balanceService.accruePercentageToBalances();
    }


}
