package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.port.in.Usuario.CrearUsuarioUseCasePort;
import com.datacenter.mallaturnos.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Service;

/**
 * Use Case: Crear un nuevo usuario en el sistema
 */
@Service
public class CrearUsuarioUseCase implements CrearUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;

    public CrearUsuarioUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearUsuario(String nombre,
                                String tipoDocumento,
                                Integer numeroDocumento,
                                Integer contrasena,
                                Long rolId,
                                Long cargoId,
                                Long areaId) {

        if (usuarioRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new IllegalArgumentException("El número de documento ya existe");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setTipoDocumento(tipoDocumento);
        nuevoUsuario.setNumeroDocumento(numeroDocumento);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setRolId(rolId);
        nuevoUsuario.setCargoId(cargoId);
        nuevoUsuario.setAreaId(areaId);

        return usuarioRepository.save(nuevoUsuario);
    }
}