package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

import org.springframework.stereotype.Service;

@Service
public class SolicitarEliminacionTurnoUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final AsignacionRepositoryPort asignacionRepository;

    public SolicitarEliminacionTurnoUseCase(SolicitudRepositoryPort solicitudRepository,
                                           AsignacionRepositoryPort asignacionRepository,
                                           TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.solicitudRepository = solicitudRepository;
        this.asignacionRepository = asignacionRepository;
    }

    public SolicitudTurno ejecutar(Long asignacionId, Long funcionarioId, Long tipoSolicitudId,
                                   String motivoSolicitud) {
        
        if (!asignacionRepository.findById(asignacionId).isPresent()) {
            throw new IllegalArgumentException("Asignación no encontrada");
        }

        SolicitudTurno nuevaSolicitud = new SolicitudTurno();
        nuevaSolicitud.setAsignacionTurnoId(asignacionId);
        nuevaSolicitud.setTipoSolicitudId(tipoSolicitudId);
        nuevaSolicitud.setMotivoSolicitud(motivoSolicitud);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(nuevaSolicitud);
    }
}
