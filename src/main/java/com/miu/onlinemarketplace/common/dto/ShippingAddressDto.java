package com.miu.onlinemarketplace.common.dto;

import com.miu.onlinemarketplace.common.enums.ShippingStatus;
import com.miu.onlinemarketplace.entities.Address;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddressDto {

    private Long shoppingId;
    private String deliveryInstruction;
    @Enumerated(value = EnumType.STRING)
    private ShippingStatus shippingStatus;
    private AddressDto address;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
