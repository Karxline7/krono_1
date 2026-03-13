package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;

import java.time.LocalDate;
import java.util.List;

public interface ListarAsignacionesPorFechaUseCasePort {

    List<AsignacionTurno> listarAsignacionesPorFecha(LocalDate fecha);
}
