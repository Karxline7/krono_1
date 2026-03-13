package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;

import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;

public interface EditarTipoSolicitudUseCasePort {

    TipoSolicitudDto editarTipoSolicitud(Long id, TipoSolicitudDto tipoSolicitudDto);
}
