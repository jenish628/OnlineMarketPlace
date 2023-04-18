package com.miu.onlinemarketplace.config;

import com.miu.onlinemarketplace.service.accountcommission.AccountCommissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private AccountCommissionService accountCommissionService;

    public ScheduledTasks(AccountCommissionService accountCommissionService) {
        this.accountCommissionService = accountCommissionService;
    }

    @Scheduled(cron = "0 0 12 * * ?") // At 12:00 p.m. (noon) every day
    public void saveCommission() {
        accountCommissionService.saveCommission();
        log.info("Schedule Start ......");
    }
}
