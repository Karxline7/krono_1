package com.datacenter.krono_12.application.UseCase.Solicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.domain.model.EstadoSolicitud;
import com.datacenter.krono_12.domain.model.SolicitudTurno;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.in.solicitud.DenegarSolicitudUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.SolicitudRepositoryPort;

@Service
public class DenegarSolicitudUseCase implements DenegarSolicitudUseCasePort {

    private final SolicitudRepositoryPort solicitudRepository;
    private final SolicitudTurnoMapper solicitudTurnoMapper;

    public DenegarSolicitudUseCase(SolicitudRepositoryPort solicitudRepository,
                                   SolicitudTurnoMapper solicitudTurnoMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudTurnoMapper = solicitudTurnoMapper;
    }

    @Override
    public SolicitudTurnoDto denegarSolicitud(Long solicitudId) {

        SolicitudTurno solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new IllegalArgumentException("La solicitud ya se encuentra resuelta");
        }

        solicitud.setEstado(EstadoSolicitud.DENEGADA);

        SolicitudTurno actualizada = solicitudRepository.save(solicitud);

        return solicitudTurnoMapper.toDto(actualizada);
    }
}
