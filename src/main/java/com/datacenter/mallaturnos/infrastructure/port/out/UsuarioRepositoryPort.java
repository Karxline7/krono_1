package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de usuarios.
 * El dominio NO sabe cómo se implementa, solo define qué necesita.
 */
public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNumeroDocumento(Long numeroDocumento);
    boolean existsByNumeroDocumento(Long numeroDocumento);
    List<Usuario> findAll();
    List<Usuario> findByAreaIdAndRolId(Long areaId, Long rolId);
    List<Usuario> findByAreaId(Long areaId);
    List<Usuario> findByCargoId(Long cargoId);
    List<UsuarioDto> findAllWithCargo();
    void delete(Long id);
    Usuario save(Usuario usuario);
}