package com.datacenter.mallaturnos.infrastructure.port.in.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;

public interface CrearUsuarioUseCasePort {

    UsuarioDto crearUsuario(CrearUsuarioDto Dto);
}