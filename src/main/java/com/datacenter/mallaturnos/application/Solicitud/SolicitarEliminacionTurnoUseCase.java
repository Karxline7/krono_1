package com.datacenter.mallaturnos.application.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.domain.port.out.AsignacionRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class SolicitarEliminacionTurnoUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final AsignacionRepositoryPort asignacionRepository;

    public SolicitarEliminacionTurnoUseCase(SolicitudRepositoryPort solicitudRepository,
                                           AsignacionRepositoryPort asignacionRepository) {
        this.solicitudRepository = solicitudRepository;
        this.asignacionRepository = asignacionRepository;
    }

    public SolicitudTurno ejecutar(Long asignacionId, Long funcionarioId,
                                   String motivoSolicitud) {
        
        if (!asignacionRepository.findById(asignacionId).isPresent()) {
            throw new IllegalArgumentException("Asignación no encontrada");
        }

        SolicitudTurno nuevaSolicitud = new SolicitudTurno();
        nuevaSolicitud.setAsignacionTurnoId(asignacionId);
        nuevaSolicitud.setTipo(TipoSolicitud.ELIMINACION);
        nuevaSolicitud.setMotivoSolicitud(motivoSolicitud);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(nuevaSolicitud);
    }
}
