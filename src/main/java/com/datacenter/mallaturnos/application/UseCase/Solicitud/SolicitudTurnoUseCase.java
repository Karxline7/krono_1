package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

import com.datacenter.mallaturnos.infrastructure.port.in.solicitud.SolicitudTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class SolicitudTurnoUseCase implements SolicitudTurnoUseCasePort {

    private final SolicitudRepositoryPort solicitudRepository;
    private final AsignacionRepositoryPort asignacionRepository;
    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;
    private final SolicitudTurnoMapper solicitudTurnoMapper;

    public SolicitudTurnoUseCase(SolicitudRepositoryPort solicitudRepository,
                                 AsignacionRepositoryPort asignacionRepository,
                                 TipoSolicitudRepositoryPort tipoSolicitudRepository,
                                 SolicitudTurnoMapper solicitudTurnoMapper) {

        this.solicitudRepository = solicitudRepository;
        this.asignacionRepository = asignacionRepository;
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.solicitudTurnoMapper = solicitudTurnoMapper;
    }

    @Override
    public SolicitudTurnoDto crearSolicitudTurno(SolicitudTurnoDto dto) {

        Long asignacionId = dto.getAsignacionTurnoId();
        Long tipoSolicitudId = dto.getTipoSolicitudId();

        // Validar asignación
        asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));

        // Validar tipo de solicitud
        if (tipoSolicitudRepository.findById(tipoSolicitudId).isEmpty()) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }

        // Validar solicitudes existentes
        if (solicitudRepository.existsPendienteForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud pendiente para esta asignación");
        }

        if (solicitudRepository.existsAprobadaForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud aprobada para esta asignación");
        }

        if (solicitudRepository.existsDenegadaForAsignacion(asignacionId)) {
            throw new IllegalStateException("Ya existe una solicitud denegada para esta asignación");
        }

        // Crear solicitud
        SolicitudTurno nuevaSolicitud = solicitudTurnoMapper.toDomain(dto);
        nuevaSolicitud.setEstado(EstadoSolicitud.PENDIENTE);

        SolicitudTurno guardada = solicitudRepository.save(nuevaSolicitud);

        return solicitudTurnoMapper.toDto(guardada);
    }
}
