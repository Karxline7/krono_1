package com.datacenter.mallaturnos.port.in.rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import java.util.List;

public interface ListarRolesUseCasePort {

    List<Rol> listarRoles();
}