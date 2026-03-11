package com.datacenter.mallaturnos.port.in.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import java.util.Optional;

public interface ObtenerUsuarioUseCasePort {

    Optional<Usuario> obtenerUsuario(Long id);

    Optional<Usuario> obtenerPorNumeroDocumento(Integer numeroDocumento);
}