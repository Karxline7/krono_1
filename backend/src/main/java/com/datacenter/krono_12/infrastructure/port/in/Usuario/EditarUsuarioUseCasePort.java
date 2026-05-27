package com.datacenter.krono_12.infrastructure.port.in.Usuario;

import com.datacenter.krono_12.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;

public interface EditarUsuarioUseCasePort {

    UsuarioDto editarUsuario(EditarUsuarioDto Dto);
}
