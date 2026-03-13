package com.datacenter.mallaturnos.infrastructure.port.in.turno;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import java.util.Optional;

public interface ObtenerTurnoUseCasePort {

    Optional<TurnoDto> obtenerTurno(Long id);
}
