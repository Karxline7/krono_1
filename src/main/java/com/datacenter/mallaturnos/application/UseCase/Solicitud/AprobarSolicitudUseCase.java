package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

import com.datacenter.mallaturnos.infrastructure.port.in.solicitud.AprobarSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class AprobarSolicitudUseCase implements AprobarSolicitudUseCasePort {

    private final SolicitudRepositoryPort solicitudRepository;
    private final SolicitudTurnoMapper solicitudTurnoMapper;

    public AprobarSolicitudUseCase(SolicitudRepositoryPort solicitudRepository,
                                   SolicitudTurnoMapper solicitudTurnoMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudTurnoMapper = solicitudTurnoMapper;
    }

    @Override
    public SolicitudTurnoDto aprobarSolicitud(Long solicitudId) {

        SolicitudTurno solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new IllegalArgumentException("La solicitud ya se encuentra resuelta");
        }

        solicitud.setEstado(EstadoSolicitud.APROBADA);

        SolicitudTurno actualizada = solicitudRepository.save(solicitud);

        return solicitudTurnoMapper.toDto(actualizada);
    }
}