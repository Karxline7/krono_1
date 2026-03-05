package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.RolJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA para Roles
 */
@Repository
public interface RolJpaRepository extends JpaRepository<RolJpaEntity, Long> {

    Optional<RolJpaEntity> findByNombre(String nombre);
    
}
