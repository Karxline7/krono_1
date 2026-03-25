package com.datacenter.mallaturnos.infrastructure.port.in.turno;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;

public interface CrearTurnoUseCasePort {

    TurnoDto crearTurno(TurnoDto turnoDto);
}
