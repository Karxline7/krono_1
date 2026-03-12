package com.datacenter.mallaturnos.port.in.solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

public interface DenegarSolicitudUseCasePort {

    SolicitudTurno denegarSolicitud(Long solicitudId);
}