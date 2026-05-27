package com.datacenter.krono_12.infrastructure.port.in.rol;

import java.util.List;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;

public interface ListarRolesUseCasePort {

    List<RolDto> listarRoles();
}