package com.example.testjasper.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testjasper.model.dto.DefaultDto;
import com.example.testjasper.model.dto.ObjetoDto;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.awt.Color;
import java.io.IOException;

@Service
public class DefaultService {

    @Autowired
    private RelatorioService relatorioService;
    
    public List<DefaultDto> findAll(){
        return List.of(
            new DefaultDto(
            "Título Geral",
                List.of(
                    new ObjetoDto(1L,"Título 1",10,LocalDateTime.now().minusDays(2L)),
                    new ObjetoDto(2L,"Título 2",20,LocalDateTime.now().minusDays(4L))
                )
            ),
            new DefaultDto(
            "Título Geral 2",
                List.of(
                    new ObjetoDto(1L,"Título 2.1",15,LocalDateTime.now().minusDays(5L)),
                    new ObjetoDto(2L,"Título 2.2",25,LocalDateTime.now().minusDays(10L))
                )
            )
        );
    }

    public void export(HttpServletResponse response){
        List<DefaultDto> list = findAll();
        relatorioService.exportXLSX(list, new HashMap<>(), "/static/templates/jasper/Blank_A4.jasper", response);
    }

    private SimpleXlsxReportConfiguration getConfiguration() {
        var xlsReportConfiguration = new SimpleXlsxReportConfiguration();
        xlsReportConfiguration.setSheetTabColor(Color.BLUE);
        xlsReportConfiguration.setSheetNames(new String[]{"teste"});
        xlsReportConfiguration.setShowGridLines(false);
        xlsReportConfiguration.setAutoFitPageHeight(true);
        xlsReportConfiguration.setWrapText(true);
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setIgnoreCellBorder(true);
        xlsReportConfiguration.setImageBorderFixEnabled(false);
        xlsReportConfiguration.setDetectCellType(true);
        return xlsReportConfiguration;
    }
}
