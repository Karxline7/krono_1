package com.datacenter.krono_12.infrastructure.port.in.turno;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;

public interface CrearTurnoUseCasePort {

    TurnoDto crearTurno(TurnoDto turnoDto);
}
