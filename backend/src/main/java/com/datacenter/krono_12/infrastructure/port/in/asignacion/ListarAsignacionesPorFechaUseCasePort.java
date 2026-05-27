package com.datacenter.krono_12.infrastructure.port.in.asignacion;

import java.time.LocalDate;
import java.util.List;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;

public interface ListarAsignacionesPorFechaUseCasePort {

    List<AsignacionTurnoDto> listarAsignacionesPorFecha(LocalDate fecha);
}
