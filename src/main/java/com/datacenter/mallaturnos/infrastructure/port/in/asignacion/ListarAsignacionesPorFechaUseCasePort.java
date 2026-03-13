package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;

import java.time.LocalDate;
import java.util.List;

public interface ListarAsignacionesPorFechaUseCasePort {

    List<AsignacionTurnoDto> listarAsignacionesPorFecha(LocalDate fecha);
}
