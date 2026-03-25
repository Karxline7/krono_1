package com.datacenter.mallaturnos.infrastructure.port.in.rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;

public interface CrearRolUseCasePort {

    RolDto crearRol(RolDto dto);
}
