package com.miu.onlinemarketplace.service.report;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@Service
@Slf4j
public class ReportExporterServiceImpl implements ReportExporterService {

    private static JasperPrint getJasperPrint(String report, Map<String, Object> parameters) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("jasperreports/" + report + ".jrxml");
        return JasperFillManager.fillReport(JasperCompileManager.compileReport(file.getAbsolutePath()), parameters);
    }

    @Override
    public byte[] exportToPdf(String report, Map<String, Object> data) throws FileNotFoundException, JRException {
        JasperPrint jasperPrint = getJasperPrint(report, data);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public byte[] exportToHtml(String report, Map<String, Object> data) throws FileNotFoundException, JRException {
        JasperPrint jasperPrint = getJasperPrint(report, data);
        HtmlExporter htmlExporter = new HtmlExporter();
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
        htmlExporter.exportReport();
        return out.toByteArray();
    }
}
