package com.datacenter.mallaturnos.infrastructure.port.in.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import java.util.Optional;

public interface ObtenerUsuarioUseCasePort {

    Optional<UsuarioDto> obtenerUsuario(Long id);

    Optional<UsuarioDto> obtenerPorNumeroDocumento(Integer numeroDocumento);
}