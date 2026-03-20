package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.CargoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA para Cargos
 */
@Repository
public interface CargoJpaRepository extends JpaRepository<CargoJpaEntity, Long> {
    /**
     * Evita depender del mapeo completo de la entidad (nombre cargado).
     * Lee directamente el campo `nombre` desde la tabla `cargo`.
     */
    @Query(value = "SELECT nombre FROM cargo WHERE id = :id", nativeQuery = true)
    String findNombreById(@Param("id") Long id);
}
