package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Editar un usuario existente
 */
@Service
public class EditarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public EditarUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Edita un usuario existente
     * @param id ID del usuario a editar
     * @param nombre Nuevos nombres
     * @param tipodocumento Nuevo tipo de documento
     * @param contrasena Nueva contraseña
     * @param rolId ID del nuevo rol
     * @param cargoId Nuevo cargo ID
     * @param areaId Nuevo área ID
     * @param activo Nuevo estado
     * @return Usuario editado
     */
    public Usuario ejecutar(Long id, String nombre, String tipodocumento,
                            Integer contrasena, Long rolId, Long cargoId, 
                            Long areaId) {
        
        // Obtener usuario existente
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        Usuario usuario = usuarioExistente.get();

        // Actualizar campos
        usuario.setNombre(nombre);
        usuario.setTipoDocumento(tipodocumento);
        usuario.setContrasena(contrasena);
        usuario.setRolId(rolId);
        usuario.setCargoId(cargoId);
        usuario.setAreaId(areaId);

        // Guardar y retornar
        return usuarioRepository.save(usuario);
    }
}
