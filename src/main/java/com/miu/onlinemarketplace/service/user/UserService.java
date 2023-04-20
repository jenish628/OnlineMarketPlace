package com.miu.onlinemarketplace.service.user;

import com.miu.onlinemarketplace.common.dto.ResponseDto;
import com.miu.onlinemarketplace.common.dto.UserCardInfoDto;
import com.miu.onlinemarketplace.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {

    UserCardInfoDto findInfoById(Long id);

}
