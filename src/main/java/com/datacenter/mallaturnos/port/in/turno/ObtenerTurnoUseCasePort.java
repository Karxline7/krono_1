package com.datacenter.mallaturnos.port.in.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import java.util.Optional;

public interface ObtenerTurnoUseCasePort {

    Optional<Turno> obtenerTurno(Long id);
}
