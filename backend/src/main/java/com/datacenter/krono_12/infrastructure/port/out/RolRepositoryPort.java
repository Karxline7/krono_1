package com.datacenter.krono_12.infrastructure.port.out;

import java.util.List;
import java.util.Optional;

import com.datacenter.krono_12.domain.model.Rol;

/**
 * Puerto de salida: Define el contrato para persistencia de roles
 */
public interface RolRepositoryPort {
    Optional<Rol> findById(Long id);
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findAll();
    Rol save(Rol rol);
    void delete(Long id);
}
