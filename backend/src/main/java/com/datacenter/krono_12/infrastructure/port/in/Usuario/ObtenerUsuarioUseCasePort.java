package com.datacenter.krono_12.infrastructure.port.in.Usuario;

import java.util.Optional;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;

public interface ObtenerUsuarioUseCasePort {

    Optional<UsuarioDto> obtenerUsuario(Long id);

    Optional<UsuarioDto> obtenerPorNumeroDocumento(Long numeroDocumento);
}