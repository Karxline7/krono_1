package com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.RolJpaEntity;

import java.util.Optional;

/**
 * Repository JPA para Roles
 */
@Repository
public interface RolJpaRepository extends JpaRepository<RolJpaEntity, Long> {

    Optional<RolJpaEntity> findByNombre(String nombre);
    
}
