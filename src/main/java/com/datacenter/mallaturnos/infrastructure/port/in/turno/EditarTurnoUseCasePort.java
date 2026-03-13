package com.datacenter.mallaturnos.infrastructure.port.in.turno;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;


public interface EditarTurnoUseCasePort {

    TurnoDto editarTurno(Long id, TurnoDto turnoDto);
}
