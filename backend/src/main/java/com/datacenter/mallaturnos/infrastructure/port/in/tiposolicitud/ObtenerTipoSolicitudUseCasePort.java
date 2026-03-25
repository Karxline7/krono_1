package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;

import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import java.util.Optional;

public interface ObtenerTipoSolicitudUseCasePort {
    
    Optional<TipoSolicitudDto> obtenerTipoSolicitud(Long id);
}
