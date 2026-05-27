package com.datacenter.krono_12.infrastructure.port.in.rol;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;

public interface EditarRolUseCasePort {

    RolDto editarRol(Long id, RolDto dto);
}
