package com.datacenter.krono_12.infrastructure.port.out;

import java.util.List;
import java.util.Map;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;

public interface ExcelGeneratorPort {

    byte[] generarExcelAsignaciones(List<AsignacionTurnoDto> asignaciones, Map<Long, String> funcionarioNames, Map<Long, String> turnoNames);

    byte[] generarExcelAsignacionesPorUsuario(String nombreUsuario, List<AsignacionTurnoDto> asignaciones, Map<Long, String> turnoNames);
}
