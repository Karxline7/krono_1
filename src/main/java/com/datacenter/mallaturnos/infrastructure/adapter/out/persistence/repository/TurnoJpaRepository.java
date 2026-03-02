package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.TurnoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA para Turnos
 */
@Repository
public interface TurnoJpaRepository extends JpaRepository<TurnoJpaEntity, Long> {
}