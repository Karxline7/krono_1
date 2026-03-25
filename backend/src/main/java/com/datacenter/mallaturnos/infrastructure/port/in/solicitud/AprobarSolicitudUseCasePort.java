package com.datacenter.mallaturnos.infrastructure.port.in.solicitud;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;

public interface AprobarSolicitudUseCasePort {

    SolicitudTurnoDto aprobarSolicitud(Long solicitudId);

}