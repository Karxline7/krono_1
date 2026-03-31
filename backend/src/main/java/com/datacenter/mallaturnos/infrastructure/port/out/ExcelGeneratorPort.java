package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import java.util.List;

public interface ExcelGeneratorPort {

    byte[] generarExcelAsignaciones(List<AsignacionTurnoDto> asignaciones);

    byte[] generarExcelAsignacionesPorUsuario(String nombreUsuario, List<AsignacionTurnoDto> asignaciones);
}
