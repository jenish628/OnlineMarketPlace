package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;
import com.miu.onlinemarketplace.service.report.ReportDataRetrieveService;
import com.miu.onlinemarketplace.service.report.ReportService;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportDataRetrieveService reportDataRetrieveService;

    public ReportController(ReportService reportService, ReportDataRetrieveService reportDataRetrieveService) {
        this.reportService = reportService;
        this.reportDataRetrieveService = reportDataRetrieveService;
    }

    @NotNull
    private static ResponseEntity<byte[]> getResponseEntity(byte[] bytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Report.pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, 200);
    }

    @GetMapping("/vendor/product/sales")
    public List<VendorProductReportDto> getProductSalesReportVendor(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.productReportForVendor(fromDate, toDate);
    }

    @GetMapping("/admin/vendor/sales")
    public List<AdminVendorReportDto> getVendorSalesReportAdmin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.vendorReportForAdmin(fromDate, toDate);
    }

    @GetMapping("/admin/product/sales")
    public List<AdminProductReportDto> getProductSalesReportAdmin(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return reportDataRetrieveService.productReportForAdmin(fromDate, toDate);
    }

    @GetMapping("/vendor/product/sales/pdf")
    public ResponseEntity<byte[]> getProductSalesReportVendorPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getProductSalesReportForVendor(fromDate, toDate);
        return getResponseEntity(bytes);
    }

    @GetMapping("/admin/vendor/sales/pdf")
    public ResponseEntity<byte[]> getVendorSalesReportAdminPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getProductSalesReportForVendor(fromDate, toDate);
        return getResponseEntity(bytes);
    }

    @GetMapping("/admin/product/sales/pdf")
    public ResponseEntity<byte[]> getProductSalesReportAdminPdf(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        byte[] bytes = reportService.getProductSalesReportForVendor(fromDate, toDate);
        return getResponseEntity(bytes);
    }
}
