package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import java.time.LocalDate;

public interface AsignarTurnoUseCasePort {

    AsignacionTurno asignarTurno(Long funcionarioId,
                                 Long turnoId,
                                 LocalDate fecha);
}