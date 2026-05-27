package com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.TurnoJpaEntity;

import java.util.Optional;
/**
 * Repository JPA para Turnos
 */
@Repository
public interface TurnoJpaRepository extends JpaRepository<TurnoJpaEntity, Long> {

    Optional<TurnoJpaEntity> findByNombre(String nombre);
}