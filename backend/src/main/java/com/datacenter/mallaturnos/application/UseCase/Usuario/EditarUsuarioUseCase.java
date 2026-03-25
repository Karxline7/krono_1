package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.in.Usuario.EditarUsuarioUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Editar un usuario existente
 */
@Service
public class EditarUsuarioUseCase implements EditarUsuarioUseCasePort {

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
    @Override
    public UsuarioDto editarUsuario(EditarUsuarioDto dto) {
        Long id = dto.getId();
        String nombre = dto.getNombre();
        String tipoDocumento = dto.getTipoDocumento();
        Long rolId = dto.getRolId();
        Long cargoId = dto.getCargoId();
        Long areaId = dto.getAreaId();

        // Obtener usuario existente
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        Usuario usuario = usuarioExistente.get();

        // Actualizar campos
        usuario.setNombre(nombre);
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setRolId(rolId);
        usuario.setCargoId(cargoId);
        usuario.setAreaId(areaId);

        // Guardar y retornar
        Usuario usuarioEditado = usuarioRepository.save(usuario);

        UsuarioDto response = new UsuarioDto();
        response.setId(usuarioEditado.getId());
        response.setNombre(usuarioEditado.getNombre());
        response.setTipoDocumento(usuarioEditado.getTipoDocumento());
        response.setNumeroDocumento(usuarioEditado.getNumeroDocumento());
        response.setRolId(usuarioEditado.getRolId());
        response.setCargoId(usuarioEditado.getCargoId());
        response.setAreaId(usuarioEditado.getAreaId());

        return response;
    }
}
