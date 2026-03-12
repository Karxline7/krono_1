package com.datacenter.mallaturnos.port.in.rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import java.util.Optional;

public interface ObtenerRolUseCasePort {

    Optional<Rol> obtenerRol(Long id);
}
