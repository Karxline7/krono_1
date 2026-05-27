package com.datacenter.krono_12.application.UseCase.Usuario;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.in.Usuario.ObtenerUsuarioUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

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
