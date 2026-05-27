package com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.TipoSolicitudJpaEntity;

import java.util.Optional;

/**
 * Repository JPA para Tipos de Solicitud
 */
@Repository
public interface TipoSolicitudJpaRepository extends JpaRepository<TipoSolicitudJpaEntity, Long> {
    Optional<TipoSolicitudJpaEntity> findByNombre(String nombre);
}