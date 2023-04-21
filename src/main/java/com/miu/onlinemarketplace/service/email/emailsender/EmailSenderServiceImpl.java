package com.miu.onlinemarketplace.service.email.emailsender;

import com.miu.onlinemarketplace.common.dto.EmailHistorySaveDto;
import com.miu.onlinemarketplace.common.dto.ForgotPasswordMailSenderDto;
import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.common.enums.MailType;
import com.miu.onlinemarketplace.common.enums.OrderStatus;
import com.miu.onlinemarketplace.common.enums.UserStatus;
import com.miu.onlinemarketplace.entities.EmailTemplate;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.repository.UserRepository;
import com.miu.onlinemarketplace.service.email.emailhistory.EmailHistoryService;
import com.miu.onlinemarketplace.service.email.emailtemplate.EmailTemplateService;
import com.miu.onlinemarketplace.utils.MailSenderUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final EmailTemplateService emailTemplateService;
    private final EmailHistoryService emailHistoryService;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

//    private final OrderRepository orderRepository;


    public EmailSenderServiceImpl(EmailTemplateService emailTemplateService, EmailHistoryService emailHistoryService, UserRepository userRepository, JavaMailSender javaMailSender) {
        this.emailTemplateService = emailTemplateService;
        this.emailHistoryService = emailHistoryService;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    private static void createMimeMessageHelper(String fromEmail, String toEmail, MimeMessage mimeMessage, String subject, String htmlBody) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
    }

    @Override
    public Boolean sendSignupMail(SignUpMailSenderDto mailSenderDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        EmailTemplate emailTemplate = emailTemplateService.getTemplateByMailType(MailType.SIGNUP_VERIFICATION_CODE);
        EmailHistorySaveDto emailHistoryDto = new EmailHistorySaveDto();
        User user = userRepository.findByEmail(mailSenderDto.getToEmail()).orElseThrow(() -> {
            log.error("User not found with email: " + mailSenderDto.getToEmail());
            return new DataNotFoundException("User not found with email: " + mailSenderDto.getToEmail());
        });
        boolean send = true;
        try {
            String htmlBody = emailTemplate.getTemplate();

            htmlBody = htmlBody.replace("${name}", user.getFullName());
            htmlBody = htmlBody.replace("${verificationCode}", mailSenderDto.getVerificationCode());
            htmlBody = htmlBody.replace("${verificationLink}", "http://localhost:4200/verification/" + mailSenderDto.getVerificationCode());

            createMimeMessageHelper(emailTemplate.getFromEmail(), mailSenderDto.getToEmail(), mimeMessage, emailTemplate.getSubject(), htmlBody);

            emailHistoryDto.setFromEmail(emailTemplate.getFromEmail());
            emailHistoryDto.setToEmail(mailSenderDto.getToEmail());
            emailHistoryDto.setSubject(emailTemplate.getSubject());
            emailHistoryDto.setMessage(htmlBody);
            emailHistoryDto.setMailType(MailType.SIGNUP_VERIFICATION_CODE);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Send Mail using Template failed Exception to user with email {} of mail type {}: {} ", mailSenderDto.getToEmail(), MailType.SIGNUP_VERIFICATION_CODE, e.getMessage());
            send = false;
        }
        emailHistoryDto.setIsSend(send);
        emailHistoryService.saveEmailHistory(emailHistoryDto);
        return send;
    }

    @Override
    public Boolean sendForgotPasswordMail(ForgotPasswordMailSenderDto passwordMailSenderDto) {
        // verification code null means mailtype PASSWORD_CHANGE_NOTIFICATION
        JavaMailSender javaMailSender = MailSenderUtil.getInstance();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MailType mailType = MailType.PASSWORD_CHANGE_NOTIFICATION;
        if (passwordMailSenderDto.getVerificationCode() != null) mailType = MailType.FORGOT_PASSWORD_REQUEST_CODE;
        EmailTemplate emailTemplate = emailTemplateService.getTemplateByMailType(mailType);
        EmailHistorySaveDto emailHistoryDto = new EmailHistorySaveDto();
        User user = userRepository.findByEmail(passwordMailSenderDto.getToEmail()).orElseThrow(() -> {
            log.error("User not found with email: " + passwordMailSenderDto.getToEmail());
            return new DataNotFoundException("User not found with email: " + passwordMailSenderDto.getToEmail());
        });
        boolean send = true;
        try {
            String htmlBody = emailTemplate.getTemplate();

            htmlBody = htmlBody.replace("${name}", user.getFullName());
            if (passwordMailSenderDto.getVerificationCode() != null)
                htmlBody = htmlBody.replace("${code}", passwordMailSenderDto.getVerificationCode());

            createMimeMessageHelper(passwordMailSenderDto.getToEmail(), emailTemplate.getFromEmail(), mimeMessage, emailTemplate.getSubject(), htmlBody);

            emailHistoryDto.setFromEmail(emailTemplate.getFromEmail());
            emailHistoryDto.setToEmail(passwordMailSenderDto.getToEmail());
            emailHistoryDto.setSubject(emailTemplate.getSubject());
            emailHistoryDto.setMessage(htmlBody);
            emailHistoryDto.setMailType(mailType);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Send Mail using Template failed Exception {} to user with email {} of mail type {}", e.getMessage(), passwordMailSenderDto.getToEmail(), mailType);
            send = false;
        }
        emailHistoryDto.setIsSend(send);
        emailHistoryService.saveEmailHistory(emailHistoryDto);
        return send;
    }

    @Override
    public Boolean sendOrderMail(Long orderId, OrderStatus orderStatus) {
//        JavaMailSender javaMailSender = MailSenderUtil.getInstance();
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MailType mailType = MailType.PASSWORD_CHANGE_NOTIFICATION;
//        if(orderStatus == OrderStatus.CONFIRMED) mailType = MailType.ORDER_CONFIRMED;
//        else if(orderStatus == OrderStatus.SHIPPED) mailType = MailType.ORDER_SHIPPED;
//        else if(orderStatus == OrderStatus.DELIVERED) mailType = MailType.ORDER_DELIVERED;
//        else if(orderStatus == OrderStatus.CANCELED) mailType = MailType.ORDER_CANCELED;
//        EmailTemplate emailTemplate = emailTemplateService.getTemplateByMailType(mailType);
//        EmailHistorySaveDto emailHistoryDto = new EmailHistorySaveDto();
//        User user = userRepository.findByEmail(passwordMailSenderDto.getToEmail()).orElseThrow(()->{
//            log.error("User not found with email: "+passwordMailSenderDto.getToEmail());
//            return new DataNotFoundException("User not found with email: "+passwordMailSenderDto.getToEmail());
//        });
//        boolean send = true;
//        try {
//            String htmlBody = emailTemplate.getTemplate();
//
//            htmlBody = htmlBody.replace("${name}",user.getFullName());
//
//            createMimeMessageHelper(passwordMailSenderDto.getToEmail(),emailTemplate.getFromEmail(), mimeMessage, emailTemplate.getSubject(), htmlBody);
//
//            emailHistoryDto.setFromEmail(emailTemplate.getFromEmail());
//            emailHistoryDto.setToEmail(passwordMailSenderDto.getToEmail());
//            emailHistoryDto.setSubject(emailTemplate.getSubject());
//            emailHistoryDto.setMessage(htmlBody);
//            emailHistoryDto.setMailType(mailType);
//
//            javaMailSender.send(mimeMessage);
//        } catch (Exception e) {
//            log.error("Send Mail using Template failed Exception {} to user with email {} of mail type {}", e.getMessage(),passwordMailSenderDto.getToEmail(),mailType);
//            send = false;
//        }
//        emailHistoryDto.setSend(send);
//        emailHistoryService.saveEmailHistory(emailHistoryDto);
        return true;
    }

    @Override
    public Boolean sendAccountStatusMail(Long userId, UserStatus userStatus) {
        JavaMailSender javaMailSender = MailSenderUtil.getInstance();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MailType mailType;
        if (userStatus == UserStatus.VERIFIED) mailType = MailType.ACCOUNT_APPROVE_NOTIFICATION;
        else if (userStatus == UserStatus.SUSPENDED) mailType = MailType.ACCOUNT_SUSPEND_NOTIFICATION;
        else return false;
        EmailTemplate emailTemplate = emailTemplateService.getTemplateByMailType(mailType);
        EmailHistorySaveDto emailHistoryDto = new EmailHistorySaveDto();
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found with id: " + userId);
            return new DataNotFoundException("User not found with userId: " + userId);
        });
        boolean send = true;
        try {
            String htmlBody = emailTemplate.getTemplate();

            htmlBody = htmlBody.replace("${name}", user.getFullName());

            createMimeMessageHelper(user.getEmail(), emailTemplate.getFromEmail(), mimeMessage, emailTemplate.getSubject(), htmlBody);

            emailHistoryDto.setFromEmail(emailTemplate.getFromEmail());
            emailHistoryDto.setToEmail(user.getEmail());
            emailHistoryDto.setSubject(emailTemplate.getSubject());
            emailHistoryDto.setMessage(htmlBody);
            emailHistoryDto.setMailType(mailType);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Send Mail using Template failed Exception {} to user with email {} of mail type {}", e.getMessage(), user.getEmail(), mailType);
            send = false;
        }
        emailHistoryDto.setIsSend(send);
        emailHistoryService.saveEmailHistory(emailHistoryDto);
        return send;
    }
}
