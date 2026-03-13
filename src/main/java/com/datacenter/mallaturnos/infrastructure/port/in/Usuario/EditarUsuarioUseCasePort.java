package com.datacenter.mallaturnos.infrastructure.port.in.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;

public interface EditarUsuarioUseCasePort {

    UsuarioDto editarUsuario(EditarUsuarioDto Dto);
}
