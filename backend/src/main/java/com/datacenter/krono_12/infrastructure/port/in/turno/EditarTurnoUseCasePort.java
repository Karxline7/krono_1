package com.datacenter.krono_12.infrastructure.port.in.turno;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;


public interface EditarTurnoUseCasePort {

    TurnoDto editarTurno(Long id, TurnoDto turnoDto);
}
