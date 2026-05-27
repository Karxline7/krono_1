package com.datacenter.krono_12.infrastructure.port.in.solicitud;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;

public interface SolicitudTurnoUseCasePort {

    SolicitudTurnoDto crearSolicitudTurno(SolicitudTurnoDto solicitudTurnoDto);
}