package com.datacenter.krono_12.infrastructure.port.out;

import java.util.List;
import java.util.Optional;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.domain.model.Usuario;

/**
 * Puerto de salida: Define el contrato para persistencia de usuarios.
 * El dominio NO sabe cómo se implementa, solo define qué necesita.
 */
public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNumeroDocumento(Long numeroDocumento);
    boolean existsByNumeroDocumento(Long numeroDocumento);
    List<Usuario> findByAreaIdAndRolId(Long areaId, Long rolId);
    List<Usuario> findByAreaId(Long areaId);
    List<Usuario> findByCargoId(Long cargoId);
    List<UsuarioDto> findAllWithCargo();
    void delete(Long id);
    Usuario save(Usuario usuario);
    String obtenerNombrePorId(Long id);
}