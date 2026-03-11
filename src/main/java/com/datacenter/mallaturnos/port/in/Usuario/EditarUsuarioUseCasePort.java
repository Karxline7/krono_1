package com.datacenter.mallaturnos.port.in.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;

public interface EditarUsuarioUseCasePort {

    Usuario editarUsuario(Long id,
                          String nombre,
                          String tipoDocumento,
                          Integer contrasena,
                          Long rolId,
                          Long cargoId,
                          Long areaId);
}
