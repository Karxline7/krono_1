package com.datacenter.mallaturnos.application.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.domain.model.RolType;
import com.datacenter.mallaturnos.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Use Case: Crear un nuevo usuario en el sistema
 */
@Service
public class CrearUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public CrearUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea un nuevo usuario
     * @param nombre Nombres del usuario
     * @param numeroDocumento Número de documento (único)
     * @param tipoDocumento Tipo de documento (CC, CE, etc)
     * @param contrasena  Contraseña de 6 dígitos
     * @param rol Rol del usuario (ADMIN, SUPERVISOR, FUNCIONARIO)
     * @param cargo Cargo del usuario
     * @param areaId ID del área (puede ser null para ADMIN)
     * @return Usuario creado
     */
    public Usuario ejecutar(String nombre,  String tipoDocumento, Integer numeroDocumento,
                            Integer contrasena, RolType rol, Long cargoId, Long areaId) {
        
        // Validar que el número de documento no exista
        if (usuarioRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new IllegalArgumentException("El número de documento ya existe");
        }

        // Crear nuevo usuario usando constructor vacío
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setTipodocumento(tipoDocumento);
        nuevoUsuario.setNumerodocumento(numeroDocumento);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setCargoId(cargoId);
        nuevoUsuario.setAreaId(areaId);
        nuevoUsuario.setActivo(true);  // activo por defecto

        // Guardar y retornar
        return usuarioRepository.save(nuevoUsuario);
    }
}
