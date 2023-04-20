package com.miu.onlinemarketplace.service.email.emailhistory;

import com.miu.onlinemarketplace.common.dto.AllEmailHistoryPage;
import com.miu.onlinemarketplace.common.dto.EmailHistoryDto;
import com.miu.onlinemarketplace.common.dto.EmailHistorySaveDto;
import com.miu.onlinemarketplace.entities.EmailHistory;
import com.miu.onlinemarketplace.repository.EmailHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class EmailHistoryServiceImpl implements EmailHistoryService {

    private final EmailHistoryRepository emailHistoryRepository;
    private final ModelMapper modelMapper;

    public EmailHistoryServiceImpl(EmailHistoryRepository emailHistoryRepository, ModelMapper modelMapper) {
        this.emailHistoryRepository = emailHistoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveEmailHistory(EmailHistorySaveDto emailHistoryDto) {
        EmailHistory emailHistory = new EmailHistory();//TODO: implement Model Mapper
        emailHistoryRepository.save(emailHistory);
    }

    @Override
    public AllEmailHistoryPage getEmailHistoryPage(Pageable pageable) {
        Page<EmailHistory> emailHistoryPage = emailHistoryRepository.findAll(pageable);
        AllEmailHistoryPage allEmailHistoryPage = new AllEmailHistoryPage();
        allEmailHistoryPage.setTotalPage(emailHistoryPage.getTotalPages());
        allEmailHistoryPage.setTotalItem(emailHistoryPage.getTotalElements());
        allEmailHistoryPage.setEmailHistory(
                emailHistoryPage.get()
                        .map(emailHistory -> modelMapper.map(emailHistory, EmailHistoryDto.class))
                        .collect(Collectors.toList()));
        return allEmailHistoryPage;
    }


}
