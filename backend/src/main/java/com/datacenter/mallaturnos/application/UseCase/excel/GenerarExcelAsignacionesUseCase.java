package com.datacenter.mallaturnos.application.UseCase.excel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
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
    private final TurnoRepositoryPort turnoRepositoryPort;

    public GenerarExcelAsignacionesUseCase(
            ExcelGeneratorPort excelGeneratorPort,
            AsignacionRepositoryPort asignacionTurnoRepositoryPort,
            UsuarioRepositoryPort usuarioRepositoryPort,
            TurnoRepositoryPort turnoRepositoryPort) {

        this.excelGeneratorPort = excelGeneratorPort;
        this.asignacionTurnoRepositoryPort = asignacionTurnoRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.turnoRepositoryPort = turnoRepositoryPort;
    }

    @Override
    public byte[] generarExcelGeneral() {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignaciones();
        var usuarios = usuarioRepositoryPort.findAllWithCargo();
        Map<Long, String> funcionarioNames = usuarios.stream()
                .collect(Collectors.toMap(u -> u.getId(), u -> u.getNombre(), (existing, replacement) -> existing));
        
        Map<Long, String> turnoNames = turnoRepositoryPort.findAll().stream()
                .collect(Collectors.toMap(t -> t.getId(), t -> t.getNombre(), (existing, replacement) -> existing));

        return excelGeneratorPort.generarExcelAsignaciones(asignaciones, funcionarioNames, turnoNames);
    }

    @Override
    public byte[] generarExcelPorUsuario(Long usuarioId) {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignacionesPorUsuario(usuarioId);
        String nombreUsuario = usuarioRepositoryPort.obtenerNombrePorId(usuarioId);
        
        Map<Long, String> turnoNames = turnoRepositoryPort.findAll().stream()
                .collect(Collectors.toMap(t -> t.getId(), t -> t.getNombre(), (existing, replacement) -> existing));

        return excelGeneratorPort.generarExcelAsignacionesPorUsuario(nombreUsuario, asignaciones, turnoNames);
    }

    @Override
    public byte[] generarExcelPorArea(Long areaId) {
        var asignaciones = asignacionTurnoRepositoryPort.listarAsignacionesPorArea(areaId);
        
        Map<Long, String> funcionarioNames = usuarioRepositoryPort.findByAreaId(areaId).stream()
                .collect(Collectors.toMap(u -> u.getId(), u -> u.getNombre(), (existing, replacement) -> existing));
        
        Map<Long, String> turnoNames = turnoRepositoryPort.findAll().stream()
                .collect(Collectors.toMap(t -> t.getId(), t -> t.getNombre(), (existing, replacement) -> existing));

        return excelGeneratorPort.generarExcelAsignaciones(asignaciones, funcionarioNames, turnoNames);
    }
}
