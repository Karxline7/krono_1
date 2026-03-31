package com.datacenter.mallaturnos.application.UseCase.excel;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.port.in.excel.GenerarExcelAsignacionesUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.in.excel.GenerarExcelAsignacionesUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.ExcelGeneratorPort;

@Service
public class GenerarExcelAsignacionesUseCase implements GenerarExcelAsignacionesUseCasePort {
private final ExcelGeneratorPort excelGeneratorPort;
    private final AsignacionRepositoryPort asignacionTurnoRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public GenerarExcelAsignacionesUseCase(
            ExcelGeneratorPort excelGeneratorPort,
            AsignacionRepositoryPort asignacionTurnoRepositoryPort,
            UsuarioRepositoryPort usuarioRepositoryPort) {

        this.excelGeneratorPort = excelGeneratorPort;
        this.asignacionTurnoRepositoryPort = asignacionTurnoRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public byte[] generarExcelGeneral() {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignaciones();
        return excelGeneratorPort.generarExcelAsignaciones(asignaciones);
    }

    @Override
    public byte[] generarExcelPorUsuario(Long usuarioId) {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignacionesPorUsuario(usuarioId);
        String nombreUsuario = usuarioRepositoryPort.obtenerNombrePorId(usuarioId);
        return excelGeneratorPort.generarExcelAsignacionesPorUsuario(nombreUsuario, asignaciones);
    }

    @Override
    public byte[] generarExcelPorArea(Long areaId) {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignacionesPorArea(areaId);
        return excelGeneratorPort.generarExcelAsignaciones(asignaciones);
    }
}
