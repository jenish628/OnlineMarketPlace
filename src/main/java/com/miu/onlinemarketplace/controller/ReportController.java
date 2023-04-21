package com.miu.onlinemarketplace.controller;


import com.miu.onlinemarketplace.common.dto.SignUpMailSenderDto;
import com.miu.onlinemarketplace.service.email.emailsender.EmailSenderService;
import com.miu.onlinemarketplace.service.report.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final EmailSenderService emailSenderService;

    public ReportController(ReportService reportService, EmailSenderService emailSenderService) {
        this.reportService = reportService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/vendor/sales")
    public ResponseEntity<byte[]> getVendorSalesReport() {
        SignUpMailSenderDto dto = new SignUpMailSenderDto();
        dto.setToEmail("prabeensoti@gmail.com");
        dto.setVerificationCode("ASD456DFGG");
        emailSenderService.sendSignupMail(dto);
//        byte[] bytes = reportService.getVendorSalesReport(LocalDate.now(),LocalDate.now());
        byte[] bytes = new byte[0];
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Report.pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }
}
