package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.AsignacionTurnoJpaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository JPA para Asignaciones de Turno
 */
@Repository
public interface AsignacionJpaRepository 
        extends JpaRepository<AsignacionTurnoJpaEntity, Long> {

    Optional<AsignacionTurnoJpaEntity> 
    findByFuncionario_IdAndFecha(Long funcionarioId, LocalDate fecha);

    boolean 
    existsByFuncionario_IdAndFecha(Long funcionarioId, LocalDate fecha);

    List<AsignacionTurnoJpaEntity> 
    findByFuncionario_IdAndFechaBetween(
            Long funcionarioId,
            LocalDate inicio,
            LocalDate fin
    );

    @Query("""
        SELECT a
        FROM AsignacionTurnoJpaEntity a
        WHERE a.funcionario.area.id = :areaId
        AND a.fecha BETWEEN :inicio AND :fin
    """)
    List<AsignacionTurnoJpaEntity> findByAreaAndPeriodo(
            @Param("areaId") Long areaId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}