package com.datacenter.krono_12.infrastructure.port.in.solicitud;

import java.util.List;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;

public interface ListarSolicitudesUseCasePort {
    List<SolicitudTurnoDto> listarSolicitudes(Long id);
}
