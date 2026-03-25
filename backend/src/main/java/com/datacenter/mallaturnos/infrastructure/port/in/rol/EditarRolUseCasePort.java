package com.datacenter.mallaturnos.infrastructure.port.in.rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;

public interface EditarRolUseCasePort {

    RolDto editarRol(Long id, RolDto dto);
}
