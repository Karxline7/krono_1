package com.datacenter.mallaturnos.port.in.solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

public interface SolicitudTurnoUseCasePort {

    SolicitudTurno crearSolicitudTurno(Long asignacionId,
                                       Long tipoSolicitudId,
                                       String motivoSolicitud,
                                       EstadoSolicitud estado);
}