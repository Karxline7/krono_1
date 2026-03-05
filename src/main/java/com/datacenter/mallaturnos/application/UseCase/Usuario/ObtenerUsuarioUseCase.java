package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Obtener un usuario por ID
 */
@Service
public class ObtenerUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> ejecutar(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Obtiene un usuario por número de documento
     * @param numeroDocumento Número de documento
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> obtenerPorNumeroDocumento(Integer numeroDocumento) {
        return usuarioRepository.findByNumeroDocumento(numeroDocumento);
    }
}
