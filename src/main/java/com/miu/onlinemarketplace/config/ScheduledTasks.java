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

    public ScheduledTasks(@Autowired AccountCommissionService accountCommissionService) {
        this.accountCommissionService = accountCommissionService;
    }

//    @Scheduled(fixedRate = 5000)
    public void saveCommission() {
        accountCommissionService.saveCommission();
        log.info("Schedule Start ......");
    }
}
