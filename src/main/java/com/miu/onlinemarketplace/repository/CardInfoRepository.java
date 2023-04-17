package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.CardInfo;
import com.miu.onlinemarketplace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    CardInfo findByUserAndCardBrand(User user, String cardBrand);
}
