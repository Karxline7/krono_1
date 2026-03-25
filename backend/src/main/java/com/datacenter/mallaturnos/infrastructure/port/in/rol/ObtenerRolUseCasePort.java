package com.datacenter.mallaturnos.infrastructure.port.in.rol;

import java.util.Optional;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;

public interface ObtenerRolUseCasePort {

    Optional<RolDto> obtenerRol(Long id);
}
