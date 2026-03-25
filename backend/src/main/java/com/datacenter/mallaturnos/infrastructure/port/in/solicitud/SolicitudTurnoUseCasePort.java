package com.datacenter.mallaturnos.infrastructure.port.in.solicitud;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;

public interface SolicitudTurnoUseCasePort {

    SolicitudTurnoDto crearSolicitudTurno(SolicitudTurnoDto solicitudTurnoDto);
}