package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.domain.model.Rol;

import java.util.List;
import java.util.Optional;

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
