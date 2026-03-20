package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.infrastructure.port.in.Usuario.ObtenerUsuarioUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.mappers.UsuarioMapper;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Obtener un usuario por ID
 */
@Service
public class ObtenerUsuarioUseCase implements ObtenerUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ObtenerUsuarioUseCase(UsuarioRepositoryPort usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<UsuarioDto> obtenerUsuario(Long id) {

    return usuarioRepository.findById(id)
            .map(usuarioMapper::toDto);
    }

    /**
     * Obtiene un usuario por número de documento
     * @param numeroDocumento Número de documento
     * @return Optional con el usuario si existe
     */
    public Optional<UsuarioDto> obtenerPorNumeroDocumento(Long numeroDocumento) {

        return usuarioRepository.findByNumeroDocumento(numeroDocumento)
                .map(usuarioMapper::toDto);
    }
}
