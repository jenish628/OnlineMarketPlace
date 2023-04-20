package com.miu.onlinemarketplace.service.report;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.Map;

public interface ReportExporterService {
    byte[] exportToPdf(String report, Map<String, Object> data) throws FileNotFoundException, JRException;

    byte[] exportToHtml(String report, Map<String, Object> data) throws FileNotFoundException, JRException;
}
