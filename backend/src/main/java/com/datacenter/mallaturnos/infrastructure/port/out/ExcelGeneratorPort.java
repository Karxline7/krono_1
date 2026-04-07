package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import java.util.List;
import java.util.Map;

public interface ExcelGeneratorPort {

    byte[] generarExcelAsignaciones(List<AsignacionTurnoDto> asignaciones, Map<Long, String> funcionarioNames, Map<Long, String> turnoNames);

    byte[] generarExcelAsignacionesPorUsuario(String nombreUsuario, List<AsignacionTurnoDto> asignaciones, Map<Long, String> turnoNames);
}
