package com.datacenter.krono_12.application.UseCase.Usuario;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.in.Usuario.ListarUsuariosUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.util.List;

/**
 * Use Case: Listar usuarios
 */
@Service
public class ListarUsuariosUseCase implements ListarUsuariosUseCasePort {

    private final UsuarioRepositoryPort usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepositoryPort usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }
    /**
     * Obtiene todos los usuarios
     * @return Lista de usuarios de la base de datos
     */
    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAllWithCargo();
    }
    /**
     * Obtiene todos los usuarios de un área
     * @param areaId ID del área
     * @return Lista de usuarios del área
     */
    @Override
    public List<UsuarioDto> listarPorArea(Long areaId) {

        return usuarioRepository.findByAreaId(areaId)
                .stream()
                .map(usuarioMapper::toDto)
                .toList();
    }
    /**
     * Obtiene todos los usuarios de un cargo
     * @param cargoId ID del cargo
     * @return Lista de usuarios del cargo
     */
    public List<UsuarioDto> listarPorCargo(Long cargoId) {

    return usuarioRepository.findByCargoId(cargoId)
            .stream()
            .map(usuarioMapper::toDto)
            .toList();
    }

    /**
     * Obtiene usuarios de un área con un rol específico
     * @param areaId ID del área
     * @param rolId ID del rol a filtrar
     * @return Lista de usuarios con ese rol en el área
     */
    public List<UsuarioDto> listarPorAreaYRol(Long areaId, Long rolId) {

    return usuarioRepository.findByAreaIdAndRolId(areaId, rolId)
            .stream()
            .map(usuarioMapper::toDto)
            .toList();
    }
}
