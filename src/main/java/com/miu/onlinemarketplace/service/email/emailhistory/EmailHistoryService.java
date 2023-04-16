package com.miu.onlinemarketplace.service.email.emailhistory;

import com.miu.onlinemarketplace.common.dto.AllEmailHistoryPage;
import com.miu.onlinemarketplace.common.dto.EmailHistorySaveDto;
import org.springframework.data.domain.Pageable;

public interface EmailHistoryService {
    void saveEmailHistory(EmailHistorySaveDto emailHistorySaveDto);

    AllEmailHistoryPage getEmailHistoryPage(Pageable pageable);
}
