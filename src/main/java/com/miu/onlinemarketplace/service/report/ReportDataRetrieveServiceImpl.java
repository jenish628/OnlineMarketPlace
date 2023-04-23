package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.common.dto.AdminProductReportDto;
import com.miu.onlinemarketplace.common.dto.AdminVendorReportDto;
import com.miu.onlinemarketplace.common.dto.VendorProductReportDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportDataRetrieveServiceImpl implements ReportDataRetrieveService {
    @Override
    public List<VendorProductReportDto> productReportForVendor(LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public List<AdminProductReportDto> productReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public List<AdminVendorReportDto> vendorReportForAdmin(LocalDate fromDate, LocalDate toDate) {
        return null;
    }
}
