package com.datacenter.mallaturnos.infrastructure.port.in.rol;

import java.util.List;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;

public interface ListarRolesUseCasePort {

    List<RolDto> listarRoles();
}