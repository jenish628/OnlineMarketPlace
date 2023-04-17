package com.miu.onlinemarketplace.service.accountcommission;

import com.miu.onlinemarketplace.entities.OrderItem;

import java.util.List;

public interface AccountCommissionService {

    boolean saveCommission(List<OrderItem> orderItem);
}
