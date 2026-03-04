package com.datacenter.mallaturnos.application.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.domain.port.out.AsignacionRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class SolicitarCambioTurnoUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final AsignacionRepositoryPort asignacionRepository;

    public SolicitarCambioTurnoUseCase(SolicitudRepositoryPort solicitudRepository,
                                       AsignacionRepositoryPort asignacionRepository) {
        this.solicitudRepository = solicitudRepository;
        this.asignacionRepository = asignacionRepository;
    }

    public SolicitudTurno ejecutar(Long asignacionId,
                                   Long funcionarioId,
                                   String motivoSolicitud) {

        // Validar que la asignación exista
        var asignacion = asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));

        // Validar que la asignación pertenezca al funcionario
        if (!asignacion.getFuncionarioId().equals(funcionarioId)) {
            throw new IllegalArgumentException("La asignación no pertenece al funcionario");
        }

        // Validar que no exista una solicitud pendiente
        if (solicitudRepository.existsPendienteForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud pendiente para esta asignación");
        }

        // Validar que no exista una solicitud aprobada
        if (solicitudRepository.existsAprobadaForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud aprobada para esta asignación");
        }
        // Validar que no exista una solicitud denegada
        if (solicitudRepository.existsDenegadaForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud denegada para esta asignación");
        }

        // Crear nueva solicitud
        SolicitudTurno nuevaSolicitud = new SolicitudTurno();
        nuevaSolicitud.setAsignacionTurnoId(asignacionId);
        nuevaSolicitud.setTipo(TipoSolicitud.CAMBIO);
        nuevaSolicitud.setMotivoSolicitud(motivoSolicitud);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(nuevaSolicitud);
    }
}
