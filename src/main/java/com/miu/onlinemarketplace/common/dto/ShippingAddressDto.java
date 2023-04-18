package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.ShippingStatus;
import com.miu.onlinemarketplace.entities.Address;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddressDto {

    private Long shoppingId;
    private String deliveryInstruction;
    @Enumerated
    private ShippingStatus shippingStatus;
    private Address address;
}
