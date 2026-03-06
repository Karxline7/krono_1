package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository JPA: Spring Data genera automáticamente las consultas
 */
@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Long> {
    
    Optional<UsuarioJpaEntity> findByNumeroDocumento(Integer numeroDocumento);
    
    boolean existsByNumeroDocumento(Integer numeroDocumento);
    
    List<UsuarioJpaEntity> findByAreaIdAndRolId(Long areaId, Long rolId);
    
    List<UsuarioJpaEntity> findByAreaId(Long areaId);

    List<UsuarioJpaEntity> findByCargoId(Long cargoId);

}
