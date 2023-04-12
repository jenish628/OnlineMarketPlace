package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long userId;
    private String email;
    private String password;

    @Enumerated
    private UserStatus userStatus;
}
