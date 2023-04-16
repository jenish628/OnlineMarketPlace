package com.miu.onlinemarketplace.service.email.emailsender;

import com.miu.onlinemarketplace.common.dto.ForgotPasswordMailSenderDto;
import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.common.enums.UserStatus;

public interface EmailSenderService {
    Boolean sendSignupMail(SignUpMailSenderDto signUpMailSenderDto);

    Boolean sendForgotPasswordMail(ForgotPasswordMailSenderDto passwordMailSenderDto);

    Boolean sendOrderMail(Long orderId, OrderStatus orderStatus);

    Boolean sendAccountStatusMail(Long userId, UserStatus userStatus);
}
