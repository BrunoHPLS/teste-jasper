package com.example.testjasper.service;

import com.example.testjasper.exceptions.GeracaoPdfException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Service
public class RelatorioService {

    public byte[] gerarRelatorioPdf(Collection<?> dados, Map<String, Object> parametros,
                                    String pathArquivoFonte) {

        JasperPrint jasperPrint = getJasperPrint(pathArquivoFonte, parametros, dados);

        try {

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {

            throw new GeracaoPdfException(
                    String.format("Erro ao exportar dados para geração Relatório PDF. message: %s Cause: %s",
                            e.getMessage(), e.getCause()));
        }

    }

    public void exportXLSX(Collection<?> dados, Map<String, Object> parametros, String pathArquivoFonte, HttpServletResponse response){
        try {
            JasperPrint jasperPrint = getJasperPrint(pathArquivoFonte, parametros, dados);
            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[] { "sheet1" });
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");
            response.setContentType("application/octet-stream");
            exporter.exportReport();
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private JasperPrint getJasperPrint(String pathRelatorio, Map<String, Object> parametros,
                                       Collection<?> dados) {

        try {

            InputStream inputStream = getClass().getResourceAsStream(pathRelatorio);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            JRDataSource dataSource = new JRBeanCollectionDataSource(dados);

            return JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        } catch (Exception e) {

            throw new GeracaoPdfException(
                    String.format("Erro ao obter arquivo fonte para gerar Relatório. message: %s Cause: %s",
                            e.getMessage(), e.getCause()));

        }

    }
}
