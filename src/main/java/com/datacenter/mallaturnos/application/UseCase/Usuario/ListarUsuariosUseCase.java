package com.datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.domain.model.Rol;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use Case: Listar usuarios
 */
@Service
public class ListarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public ListarUsuariosUseCase(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los usuarios de un área
     * @param areaId ID del área
     * @return Lista de usuarios del área
     */
    public List<Usuario> listarPorArea(Long areaId) {
        return usuarioRepository.findByAreaId(areaId);
    }
    /**
     * Obtiene todos los usuarios de un cargo
     * @param cargoId ID del cargo
     * @return Lista de usuarios del cargo
     */
    public List<Usuario> listarPorCargo(Long cargoId) {
        return usuarioRepository.findByCargoId(cargoId);
    }

    /**
     * Obtiene usuarios de un área con un rol específico
     * @param areaId ID del área
     * @param rol Rol a filtrar
     * @return Lista de usuarios con ese rol en el área
     */
    public List<Usuario> listarPorAreaYRol(Long areaId, Rol rol) {
        return usuarioRepository.findByAreaIdAndRol(areaId, rol);
    }
}
