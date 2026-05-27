package com.datacenter.krono_12.infrastructure.port.in.turno;

import java.util.Optional;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;

public interface ObtenerTurnoUseCasePort {

    Optional<TurnoDto> obtenerTurno(Long id);
}
