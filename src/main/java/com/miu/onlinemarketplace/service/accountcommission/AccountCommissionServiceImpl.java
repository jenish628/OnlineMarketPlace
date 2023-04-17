package com.miu.onlinemarketplace.service.accountcommission;

import com.miu.onlinemarketplace.entities.AccountCommission;
import com.miu.onlinemarketplace.entities.OrderItem;
import com.miu.onlinemarketplace.repository.accountcommission.AccountCommissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountCommissionServiceImpl implements AccountCommissionService {

    @Autowired
    private AccountCommissionRepository accountCommissionRepository;

    @Override
    public boolean saveCommission(List<OrderItem> orderItem) {

        List<AccountCommission> accountCommissions = new ArrayList<>();
        for (OrderItem o: orderItem) {
            AccountCommission accountCommission = new AccountCommission();
//            accountCommission.setOrderId(o.getOrderId().getOrderId());
            accountCommission.setVendorCommission(o.getPrice() * o.getQuantity() * 0.80 - o.getDiscount());
            accountCommission.setPlatformCommission(o.getPrice() * o.getQuantity() * 0.20 - o.getDiscount());
            accountCommissions.add(accountCommission);
        }
        try {
            accountCommissionRepository.saveAll(accountCommissions);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.info(exception.getMessage());
        }

        return true;
    }
}
