package com.datacenter.mallaturnos.domain.port.out;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.domain.model.RolType;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de usuarios.
 * El dominio NO sabe cómo se implementa, solo define qué necesita.
 */
public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNumeroDocumento(Integer numeroDocumento);
    boolean existsByNumeroDocumento(Integer numeroDocumento);
    List<Usuario> findByAreaIdAndRol(Long areaId, RolType rol);
    List<Usuario> findByAreaId(Long areaId);
    List<Usuario> findByCargoId(Long cargoId);
    void delete(Long id);
    Usuario save(Usuario usuario);
}