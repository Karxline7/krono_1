package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository;

import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.SolicitudTurnoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
