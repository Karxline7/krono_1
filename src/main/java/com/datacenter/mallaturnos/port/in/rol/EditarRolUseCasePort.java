package com.datacenter.mallaturnos.port.in.rol;

import com.datacenter.mallaturnos.domain.model.Rol;

public interface EditarRolUseCasePort {

    Rol editarRol(Long id, String nombre, String descripcion);
}
