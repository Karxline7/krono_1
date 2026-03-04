package com.datacenter.mallaturnos.application.Usuario;

import com.datacenter.mallaturnos.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Use Case: Eliminar un usuario (hard delete)
 */
@Service
public class EliminarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public EliminarUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Elimina un usuario de la BD (hard delete)
     * @param id ID del usuario a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean ejecutar(Long id) {
        
        // Verificar que el usuario existe
        if (!usuarioRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        // Eliminar usuario
        usuarioRepository.delete(id);
        
        return true;
    }
}
