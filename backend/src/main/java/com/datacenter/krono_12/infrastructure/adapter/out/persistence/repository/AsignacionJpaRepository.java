package com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.AsignacionTurnoJpaEntity;

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

    List<AsignacionTurnoJpaEntity> findByFuncionario_Id(Long funcionarioId);

    List<AsignacionTurnoJpaEntity> findByFuncionario_AreaId(Long areaId);

    List<AsignacionTurnoJpaEntity> findByFecha(LocalDate fecha);

    void deleteByTurnoId(Long turnoId);

        @Query("""
        SELECT a
        FROM AsignacionTurnoJpaEntity a
        WHERE a.funcionario.areaId = :areaId
         AND a.fecha BETWEEN :inicio AND :fin
        """)
        List<AsignacionTurnoJpaEntity> findByAreaAndPeriodo(
                Long areaId,
                LocalDate inicio,
                LocalDate fin
        );
}