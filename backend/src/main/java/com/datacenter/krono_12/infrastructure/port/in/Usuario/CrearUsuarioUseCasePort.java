package com.datacenter.krono_12.infrastructure.port.in.Usuario;

import com.datacenter.krono_12.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;

public interface CrearUsuarioUseCasePort {

    UsuarioDto crearUsuario(CrearUsuarioDto Dto);
}