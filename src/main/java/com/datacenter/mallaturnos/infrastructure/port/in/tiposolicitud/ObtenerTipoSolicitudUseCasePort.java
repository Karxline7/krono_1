package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import java.util.Optional;

public interface ObtenerTipoSolicitudUseCasePort {
    
    Optional<TipoSolicitud> obtenerTipoSolicitud(Long id);
}
