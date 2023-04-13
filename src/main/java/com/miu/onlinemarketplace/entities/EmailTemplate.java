package com.miu.onlinemarketplace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue
    private Long templateId;
    private String mailType;
    @Column(columnDefinition = "text" )
    private String template;
    private String subject;
    private String fromEmail;
}
