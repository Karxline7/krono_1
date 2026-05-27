package com.datacenter.krono_12.infrastructure.port.in.tiposolicitud;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;

public interface EditarTipoSolicitudUseCasePort {

    TipoSolicitudDto editarTipoSolicitud(Long id, TipoSolicitudDto tipoSolicitudDto);
}
