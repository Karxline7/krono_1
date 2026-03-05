package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.TipoSolicitudJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA para Tipos de Solicitud
 */
@Repository
public interface TipoSolicitudJpaRepository extends JpaRepository<TipoSolicitudJpaEntity, Long> {

    Optional<TipoSolicitudJpaEntity> findByNombre(String nombre);

}