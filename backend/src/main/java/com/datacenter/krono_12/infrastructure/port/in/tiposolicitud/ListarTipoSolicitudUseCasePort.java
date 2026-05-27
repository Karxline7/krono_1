package com.datacenter.krono_12.infrastructure.port.in.tiposolicitud;


import java.util.List;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;

public interface ListarTipoSolicitudUseCasePort {
    
    List<TipoSolicitudDto> listarTiposSolicitud();
}
