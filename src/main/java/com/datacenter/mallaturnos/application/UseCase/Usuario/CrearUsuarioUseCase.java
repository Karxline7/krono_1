package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.in.Usuario.CrearUsuarioUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.infrastructure.mappers.UsuarioMapper;

import org.springframework.stereotype.Service;

/**
 * Use Case: Crear un nuevo usuario en el sistema
 */
@Service
public class CrearUsuarioUseCase implements CrearUsuarioUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public CrearUsuarioUseCase(UsuarioRepositoryPort usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDto crearUsuario(CrearUsuarioDto dto) {

    if (usuarioRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
        throw new IllegalArgumentException("El número de documento ya existe");
    }

    Usuario nuevoUsuario = new Usuario();
    nuevoUsuario.setNombre(dto.getNombre());
    nuevoUsuario.setTipoDocumento(dto.getTipoDocumento());
    nuevoUsuario.setNumeroDocumento(dto.getNumeroDocumento());
    nuevoUsuario.setContrasena(dto.getContrasena());
    nuevoUsuario.setRolId(dto.getRolId());
    nuevoUsuario.setCargoId(dto.getCargoId());
    nuevoUsuario.setAreaId(dto.getAreaId());

    Usuario guardado = usuarioRepository.save(nuevoUsuario);

    return usuarioMapper.toDto(guardado);
}
}