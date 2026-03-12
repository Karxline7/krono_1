package com.datacenter.mallaturnos.port.in.rol;

import com.datacenter.mallaturnos.domain.model.Rol;

public interface CrearRolUseCasePort {

    Rol crearRol(String nombre, String descripcion);
}
