package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.solicitud;

import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.SolicitudTurnoJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.AsignacionTurnoJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.SolicitudJpaRepository;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.AsignacionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de salida: Implementa el puerto SolicitudRepositoryPort
 */
@Component
public class SolicitudPersistenceAdapter implements SolicitudRepositoryPort {

    private final SolicitudJpaRepository jpaRepository;
    private final AsignacionJpaRepository asignacionRepository;

    public SolicitudPersistenceAdapter(SolicitudJpaRepository jpaRepository,
                                       AsignacionJpaRepository asignacionRepository) {
        this.jpaRepository = jpaRepository;
        this.asignacionRepository = asignacionRepository;
    }

    @Override
    public Optional<SolicitudTurno> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public boolean existsDenegadaForAsignacion(Long asignacionId) {
        return jpaRepository.existsByAsignacionTurno_IdAndEstado(
                asignacionId, EstadoSolicitud.DENEGADA);
    }

    @Override
    public boolean existsPendienteForAsignacion(Long asignacionId) {
        return jpaRepository.existsByAsignacionTurno_IdAndEstado(
                asignacionId, EstadoSolicitud.PENDIENTE);
    }

    @Override
    public boolean existsAprobadaForAsignacion(Long asignacionId) {
        return jpaRepository.existsByAsignacionTurno_IdAndEstado(
                asignacionId, EstadoSolicitud.APROBADA);
    }

    @Override
    public SolicitudTurno save(SolicitudTurno solicitud) {
        SolicitudTurnoJpaEntity entity = toEntity(solicitud);
        SolicitudTurnoJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    private SolicitudTurno toDomain(SolicitudTurnoJpaEntity entity) {
        return new SolicitudTurno(
                entity.getId(),
                entity.getAsignacionTurno().getId(), // relación
                entity.getTipo(),
                entity.getMotivoSolicitud(),
                entity.getEstado()
        );
    }

    private SolicitudTurnoJpaEntity toEntity(SolicitudTurno domain) {

        AsignacionTurnoJpaEntity asignacion =
                asignacionRepository.findById(domain.getAsignacionTurnoId())
                        .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        return new SolicitudTurnoJpaEntity(
                domain.getId(),
                domain.getTipo(),
                domain.getMotivoSolicitud(),
                domain.getEstado(),
                asignacion
        );
    }
}