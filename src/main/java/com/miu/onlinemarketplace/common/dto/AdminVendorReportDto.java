package com.miu.onlinemarketplace.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminVendorReportDto {
    private Integer id;
    private String vendorName;
    private Integer quantity;
    private Double totalRevenue;
    private Double commission;
    private Double tax;
}
