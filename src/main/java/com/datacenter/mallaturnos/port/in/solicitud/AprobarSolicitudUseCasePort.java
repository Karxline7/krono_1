package com.datacenter.mallaturnos.port.in.solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

public interface AprobarSolicitudUseCasePort {

    SolicitudTurno aprobarSolicitud(Long solicitudId);

}