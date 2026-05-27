package com.datacenter.krono_12.infrastructure.port.in.tiposolicitud;

import java.util.Optional;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;

public interface ObtenerTipoSolicitudUseCasePort {
    
    Optional<TipoSolicitudDto> obtenerTipoSolicitud(Long id);
}
