package com.datacenter.krono_12.infrastructure.adapter.out.persistence.adapter;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;
import com.datacenter.krono_12.domain.model.SolicitudTurno;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.AsignacionTurnoJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.SolicitudTurnoJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.TipoSolicitudJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.AsignacionJpaRepository;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.SolicitudJpaRepository;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.TipoSolicitudJpaRepository;
import com.datacenter.krono_12.infrastructure.port.out.SolicitudRepositoryPort;

import java.util.Optional;
import java.util.List;

/**
 * Adaptador de salida: Implementa el puerto SolicitudRepositoryPort
 */
@Component
public class SolicitudPersistenceAdapter implements SolicitudRepositoryPort {

    private final SolicitudJpaRepository jpaRepository;
    private final AsignacionJpaRepository asignacionRepository;

    public SolicitudPersistenceAdapter(SolicitudJpaRepository jpaRepository,
                                       AsignacionJpaRepository asignacionRepository,
                                       TipoSolicitudJpaRepository tipoSolicitudRepository) {
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
    public List<SolicitudTurno> obtener() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public SolicitudTurno save(SolicitudTurno solicitud) {
        SolicitudTurnoJpaEntity entity = toEntity(solicitud);
        SolicitudTurnoJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private SolicitudTurno toDomain(SolicitudTurnoJpaEntity entity) {

    return new SolicitudTurno(
        entity.getId(),
        entity.getAsignacionTurno().getId(),
        entity.getTipoSolicitud().getId(),
        entity.getMotivoSolicitud(),
        entity.getEstado()
    );
}

    private SolicitudTurnoJpaEntity toEntity(SolicitudTurno domain) {

    AsignacionTurnoJpaEntity asignacion =
            asignacionRepository.findById(domain.getAsignacionTurnoId())
                    .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

    TipoSolicitudJpaEntity tipo = new TipoSolicitudJpaEntity();
    tipo.setId(domain.getTipoSolicitudId());

    return new SolicitudTurnoJpaEntity(
            domain.getId(),
            tipo,
            domain.getMotivoSolicitud(),
            domain.getEstado(),
            asignacion
    );
}
}
