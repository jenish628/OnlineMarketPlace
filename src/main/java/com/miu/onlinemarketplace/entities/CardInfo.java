package com.miu.onlinemarketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardInfoId;
    private String cardNumber;
    private Date expDate;
    private String cvc;
    private  String cardBrand;


}
