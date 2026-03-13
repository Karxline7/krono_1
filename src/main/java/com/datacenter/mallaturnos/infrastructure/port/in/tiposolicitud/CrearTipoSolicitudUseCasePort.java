package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;

import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;

public interface CrearTipoSolicitudUseCasePort {

    TipoSolicitudDto crearTipoSolicitud(TipoSolicitudDto tipoSolicitudDto);
}
