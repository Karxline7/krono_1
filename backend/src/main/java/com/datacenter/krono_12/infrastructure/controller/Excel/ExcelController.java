package com.datacenter.krono_12.infrastructure.controller.Excel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datacenter.krono_12.infrastructure.port.in.excel.GenerarExcelAsignacionesUseCasePort;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final GenerarExcelAsignacionesUseCasePort generarExcelAsignacionesUseCasePort;

    public ExcelController(GenerarExcelAsignacionesUseCasePort generarExcelAsignacionesUseCasePort) {
        this.generarExcelAsignacionesUseCasePort = generarExcelAsignacionesUseCasePort;
    }

    @GetMapping("/asignaciones")
    public ResponseEntity<byte[]> generarExcelAsignaciones() {

        byte[] excel = generarExcelAsignacionesUseCasePort.generarExcelGeneral();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=asignaciones.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }

    @GetMapping("/asignaciones/usuario/{usuarioId}")
    public ResponseEntity<byte[]> generarExcelAsignacionesPorUsuario(@PathVariable Long usuarioId) {

        byte[] excel = generarExcelAsignacionesUseCasePort.generarExcelPorUsuario(usuarioId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=asignaciones_usuario.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }
}
