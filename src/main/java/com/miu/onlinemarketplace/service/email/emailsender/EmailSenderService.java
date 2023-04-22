package com.miu.onlinemarketplace.service.email.emailsender;

import com.miu.onlinemarketplace.common.dto.ForgotPasswordMailSenderDto;
import com.miu.onlinemarketplace.common.dto.OrderMailSenderDto;
import com.miu.onlinemarketplace.common.dto.PaymentFailMailSenderDto;
import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.entities.EmailHistory;

public interface EmailSenderService {
    Boolean sendSignupMail(SignUpMailSenderDto signUpMailSenderDto);

    Boolean sendForgotPasswordMail(ForgotPasswordMailSenderDto passwordMailSenderDto);

    Boolean sendOrderMail(OrderMailSenderDto orderMailSenderDto);

    Boolean sendAccountActivateAndSuspendMail(Long userId, UserStatus userStatus);

    Boolean sendPaymentFailNotificationMail(PaymentFailMailSenderDto mailSenderDto);

    void resendMail(EmailHistory emailHistory);
}
