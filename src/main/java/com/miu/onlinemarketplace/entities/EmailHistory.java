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
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailHistoryId;
    private String mailType;
    private String message;
    private String subject;
    private String fromEmail;
    private String toEmail;
    private Date mailSendDate;

}
