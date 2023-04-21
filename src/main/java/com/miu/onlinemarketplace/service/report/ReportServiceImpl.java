package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.exception.JasperTemplateNotFound;
import com.miu.onlinemarketplace.exception.JasperTemplateProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportExporterService reportExporterService;

    public ReportServiceImpl(ReportExporterService reportExporterService) {
        this.reportExporterService = reportExporterService;
    }

    @Override
    public byte[] getVendorSalesReport(LocalDate fromDate, LocalDate toDate) {
//        <field name="createdBy" class="java.lang.String"/>
//	<field name="fromDate" class="java.lang.String"/>
//	<field name="toDate" class="java.lang.String"/>
//	<field name="id" class="java.lang.Integer"/>
//	<field name="productName" class="java.lang.String"/>
//	<field name="quantity" class="java.lang.Integer"/>
//	<field name="rate" class="java.lang.Double"/>
//	<field name="subTotal" class="java.lang.Double"/>
//	<field name="discount" class="java.lang.Double"/>
//	<field name="tax" class="java.lang.Double"/>
//	<field name="total" class="java.lang.Double"/>
//	<field name="purchaseDate" class="java.lang.String"/>
        Map<String, Object> data = new HashMap<>();
        data.put("createdBy", "Prabeen Soti");
        data.put("fromDate", "2022-02-12");
        data.put("toDate", "2022-02-12");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> product1 = new HashMap<>();
        product1.put("id", 1);
        product1.put("productName", "Mac Book 1");
        product1.put("quantity", 2);
        product1.put("rate", 220.00);
        product1.put("subTotal", 440.00);
        product1.put("discount", 0.00);
        product1.put("tax", 44.00);
        product1.put("total", 484.00);
        product1.put("purchaseDate", "2022-02-12");
        dataList.add(product1);
        Map<String, Object> product2 = new HashMap<>();
        product2.put("id", 2);
        product2.put("productName", "Mac Book 2");
        product2.put("quantity", 1);
        product2.put("rate", 220.00);
        product2.put("subTotal", 220.00);
        product2.put("discount", 10.00);
        product2.put("tax", 21.00);
        product2.put("total", 241.00);
        product2.put("purchaseDate", "2022-02-12");
        dataList.add(product2);
        try {
            return reportExporterService.exportToPdf("vendor_report", data, dataList);
        } catch (IOException e) {
            log.error("Jasper template \"vendor_report\" not found!");
            throw new JasperTemplateNotFound("Jasper template \"vendor_report\" not found!");
        } catch (JRException e) {
            log.error("Jasper template processing error:" + e.getMessage());
            throw new JasperTemplateProcessingException("Jasper template \"vendor_report\" processing error!!");
        }
    }
}
