package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.adapter;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.port.out.ExcelGeneratorPort;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExcelPersistenceAdapter implements ExcelGeneratorPort {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public byte[] generarExcelAsignaciones(List<AsignacionTurnoDto> asignaciones) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Asignaciones");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Funcionario ID");
            header.createCell(2).setCellValue("Turno ID");
            header.createCell(3).setCellValue("Fecha");

            // Data
            int rowNum = 1;

            for (AsignacionTurnoDto asignacion : asignaciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(asignacion.getId());
                row.createCell(1).setCellValue(asignacion.getFuncionarioId());
                row.createCell(2).setCellValue(asignacion.getTurnoId());
                row.createCell(3).setCellValue(asignacion.getFecha().toString());
            }
            // Auto size columnas
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel de turnos", e);
        }
    }

    @Override
    public byte[] generarExcelAsignacionesPorUsuario(String nombreUsuario, List<AsignacionTurnoDto> asignaciones) {
    try (Workbook workbook = new XSSFWorkbook()) {

        Sheet sheet = workbook.createSheet(nombreUsuario);

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Funcionario ID");
        header.createCell(2).setCellValue("Turno ID");
        header.createCell(3).setCellValue("Fecha");

        // Data rows
        int rowNum = 1;
        for (AsignacionTurnoDto asignacion : asignaciones) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(asignacion.getId());
            row.createCell(1).setCellValue(asignacion.getFuncionarioId());
            row.createCell(2).setCellValue(asignacion.getTurnoId());
            row.createCell(3).setCellValue(asignacion.getFecha().toString());
        }

        // Auto-size columns
        for (int i = 0; i <= 3; i++) {
            sheet.autoSizeColumn(i);
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            return out.toByteArray();
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al generar Excel de asignaciones", e);
    }
}

        public byte[] generarExcelTurnosPorArea(String nombreArea, List<AsignacionTurnoDto> asignaciones) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(nombreArea);

            // Header igual al DTO
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Funcionario ID");
            header.createCell(2).setCellValue("Turno ID");
            header.createCell(3).setCellValue("Fecha");

            // Llenar filas con los datos del DTO
            int rowNum = 1;
            for (AsignacionTurnoDto asignacion : asignaciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(asignacion.getId());
                row.createCell(1).setCellValue(asignacion.getFuncionarioId());
                row.createCell(2).setCellValue(asignacion.getTurnoId());
                row.createCell(3).setCellValue(asignacion.getFecha() != null ? asignacion.getFecha().toString() : "");
            }

            // Ajustar ancho de columnas automáticamente
            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convertir a byte[]
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return out.toByteArray();
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel de turnos por área", e);
        }
    }
}
