package com.miu.onlinemarketplace.service.report;

import java.time.LocalDate;

public interface ReportService {

    byte[] getVendorSalesReport(LocalDate fromDate, LocalDate toDate);
}
