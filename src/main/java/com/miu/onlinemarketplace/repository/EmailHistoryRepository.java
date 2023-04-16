package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistory, Long> {
}
