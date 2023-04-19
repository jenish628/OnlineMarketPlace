package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.MailType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EmailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailHistoryId;
    @Enumerated(EnumType.STRING)
    private MailType mailType;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String subject;
    private String fromEmail;
    private String toEmail;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @UpdateTimestamp
    private LocalDateTime mailSendDateTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User toUser;
}
