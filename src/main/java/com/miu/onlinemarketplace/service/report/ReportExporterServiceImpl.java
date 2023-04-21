package com.miu.onlinemarketplace.service.report;

import com.miu.onlinemarketplace.common.enums.ReportType;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportExporterServiceImpl implements ReportExporterService {

    private final ResourceLoader resourceLoader;

    public ReportExporterServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public byte[] exportToPdf(ReportType reportType, Map<String, Object> data, List<Map<String, Object>> dataList) throws IOException, JRException {
        JasperPrint jasperPrint;
        if (dataList.isEmpty()) {
            jasperPrint = getJasperPrint(reportType.getName(), data);
        } else {
            jasperPrint = getJasperPrint(reportType.getName(), data, dataList);
        }
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public byte[] exportToHtml(ReportType reportType, Map<String, Object> data, List<Map<String, Object>> dataList) throws IOException, JRException {
        JasperPrint jasperPrint;
        if (dataList.isEmpty()) {
            jasperPrint = getJasperPrint(reportType.getName(), data);
        } else {
            jasperPrint = getJasperPrint(reportType.getName(), data, dataList);
        }
        HtmlExporter htmlExporter = new HtmlExporter();
        htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
        htmlExporter.exportReport();
        return out.toByteArray();
    }

    private JasperPrint getJasperPrint(String report, Map<String, Object> parameters) throws IOException, JRException {
        Resource resource = resourceLoader.getResource("classpath:jasperreports/" + report + ".jrxml");
        File file = ResourceUtils.getFile(resource.getURI());
        return JasperFillManager.fillReport(JasperCompileManager.compileReport(file.getAbsolutePath()), parameters);
    }

    private JasperPrint getJasperPrint(String report, Map<String, Object> parameters, List<Map<String, Object>> dataList) throws IOException, JRException {
        JRDataSource listDataSource = new JRBeanCollectionDataSource(dataList);
        Resource resource = resourceLoader.getResource("classpath:jasperreports/" + report + ".jrxml");
        File file = ResourceUtils.getFile(resource.getURI());
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        return JasperFillManager.fillReport(jasperReport, parameters, listDataSource);
    }
}
