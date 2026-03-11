package com.datacenter.mallaturnos.port.in.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;

public interface CrearUsuarioUseCasePort {

    Usuario crearUsuario(String nombre,
                         String tipoDocumento,
                         Integer numeroDocumento,
                         Integer contrasena,
                         Long rolId,
                         Long cargoId,
                         Long areaId);
}