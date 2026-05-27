package com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.SolicitudTurnoJpaEntity;

import java.util.List;

/**
 * Repository JPA para Solicitudes de Turno
 */
@Repository
public interface SolicitudJpaRepository extends JpaRepository<SolicitudTurnoJpaEntity, Long> {
    
    List<SolicitudTurnoJpaEntity> findByAsignacionTurno_Funcionario_Id(Long funcionarioId);
    
    boolean existsByAsignacionTurno_IdAndEstado(Long asignacionId, EstadoSolicitud estado);
    
    boolean existsByAsignacionTurnoIdAndEstado(Long asignacionId, EstadoSolicitud estado);
}
