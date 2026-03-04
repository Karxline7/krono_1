package com.datacenter.mallaturnos.application.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.domain.model.RolType;
import com.datacenter.mallaturnos.domain.port.out.UsuarioRepositoryPort;
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
     * @param rol Nuevo rol
     * @param cargoId Nuevo cargo ID
     * @param areaId Nuevo área ID
     * @param activo Nuevo estado
     * @return Usuario editado
     */
    public Usuario ejecutar(Long id, String nombre, String tipodocumento,
                            Integer contrasena, RolType rol, Long cargoid, 
                            Long areaId, Boolean activo) {
        
        // Obtener usuario existente
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        Usuario usuario = usuarioExistente.get();

        // Actualizar campos
        usuario.setNombre(nombre);
        usuario.setTipodocumento(tipodocumento);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);
        usuario.setCargoId(cargoid);
        usuario.setAreaId(areaId);
        usuario.setActivo(activo);

        // Guardar y retornar
        return usuarioRepository.save(usuario);
    }
}
