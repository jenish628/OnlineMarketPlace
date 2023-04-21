package com.miu.onlinemarketplace.service.report;

import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReportExporterService {
    byte[] exportToPdf(String report, Map<String, Object> data, List<Map<String, Object>> dataList) throws IOException, JRException;

    byte[] exportToHtml(String report, Map<String, Object> data) throws IOException, JRException;
}
