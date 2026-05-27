package com.datacenter.krono_12.infrastructure.port.in.rol;

import java.util.Optional;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;

public interface ObtenerRolUseCasePort {

    Optional<RolDto> obtenerRol(Long id);
}
