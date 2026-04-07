package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.adapter;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.infrastructure.port.out.ExcelGeneratorPort;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelPersistenceAdapter implements ExcelGeneratorPort {

    // Color Lime (aprox #d4e157)
    private static final byte[] LIME_RGB = new byte[]{(byte) 212, (byte) 225, (byte) 87};

    @Override
    public byte[] generarExcelAsignaciones(List<AsignacionTurnoDto> asignaciones, Map<Long, String> funcionarioNames, Map<Long, String> turnoNames) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Malla de Turnos");

            CellStyle headerStyle = createLimeHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            // Determinar lunes de la semana actual si no hay datos
            LocalDate reference = asignaciones.isEmpty() ? LocalDate.now() : asignaciones.get(0).getFecha();
            LocalDate monday = reference.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            // Fila de encabezado: FUNCIONARIO | LUNES (DD) | ...
            Row hRow = sheet.createRow(0);
            String[] diasHeaders = {"FUNCIONARIO", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
            for (int i = 0; i < diasHeaders.length; i++) {
                Cell cell = hRow.createCell(i);
                String label = diasHeaders[i];
                if (i > 0) {
                    label += " (" + String.format("%02d", monday.plusDays(i - 1).getDayOfMonth()) + ")";
                }
                cell.setCellValue(label);
                cell.setCellStyle(headerStyle);
            }

            // Agrupar asignaciones por funcionario
            Map<Long, List<AsignacionTurnoDto>> grouping = asignaciones.stream()
                    .collect(Collectors.groupingBy(AsignacionTurnoDto::getFuncionarioId));

            // Lista ordenada de funcionarios
            List<Long> sortedIds = new ArrayList<>(funcionarioNames.keySet());
            sortedIds.sort(Comparator.comparing(funcionarioNames::get));

            int rowIdx = 1;
            for (Long fid : sortedIds) {
                Row row = sheet.createRow(rowIdx++);
                
                // Nombre
                Cell nameCell = row.createCell(0);
                nameCell.setCellValue(funcionarioNames.get(fid));
                nameCell.setCellStyle(bodyStyle);

                // Días de la semana
                List<AsignacionTurnoDto> userAsigs = grouping.getOrDefault(fid, Collections.emptyList());
                for (int d = 0; d < 7; d++) {
                    LocalDate targetDate = monday.plusDays(d);
                    Cell dCell = row.createCell(d + 1);
                    
                    String turnoVal = userAsigs.stream()
                            .filter(a -> a.getFecha().equals(targetDate))
                            .map(a -> turnoNames.getOrDefault(a.getTurnoId(), "Turno " + a.getTurnoId()))
                            .findFirst()
                            .orElse("DESCANSO");

                    dCell.setCellValue(turnoVal);
                    dCell.setCellStyle(bodyStyle);
                }
            }

            // Auto-size
            for (int i = 0; i < 8; i++) { sheet.autoSizeColumn(i); }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar Excel en formato Malla", e);
        }
    }

    @Override
    public byte[] generarExcelAsignacionesPorUsuario(String nombreUsuario, List<AsignacionTurnoDto> asignaciones, Map<Long, String> turnoNames) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Mi Reporte");

            CellStyle headerStyle = createLimeHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            LocalDate reference = asignaciones.isEmpty() ? LocalDate.now() : asignaciones.get(0).getFecha();
            LocalDate monday = reference.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            // Encabezado horizontal para el usuario individual (como en la malla)
            Row hRow = sheet.createRow(0);
            hRow.createCell(0).setCellValue("USUARIO");
            hRow.getCell(0).setCellStyle(headerStyle);
            
            String[] dias = {"LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
            for (int i = 0; i < 7; i++) {
                Cell c = hRow.createCell(i + 1);
                c.setCellValue(dias[i] + " (" + String.format("%02d", monday.plusDays(i).getDayOfMonth()) + ")");
                c.setCellStyle(headerStyle);
            }

            // Datos
            Row dRow = sheet.createRow(1);
            dRow.createCell(0).setCellValue(nombreUsuario);
            dRow.getCell(0).setCellStyle(bodyStyle);

            for (int i = 0; i < 7; i++) {
                LocalDate date = monday.plusDays(i);
                Cell cell = dRow.createCell(i + 1);
                String val = asignaciones.stream()
                        .filter(a -> a.getFecha().equals(date))
                        .map(a -> turnoNames.getOrDefault(a.getTurnoId(), "Turno " + a.getTurnoId()))
                        .findFirst()
                        .orElse("DESCANSO");
                cell.setCellValue(val);
                cell.setCellStyle(bodyStyle);
            }

            for (int i = 0; i < 8; i++) { sheet.autoSizeColumn(i); }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar Excel individual", e);
        }
    }

    private CellStyle createLimeHeaderStyle(Workbook workbook) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFColor lime = new XSSFColor(LIME_RGB, null);
        style.setFillForegroundColor(lime);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    private CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
