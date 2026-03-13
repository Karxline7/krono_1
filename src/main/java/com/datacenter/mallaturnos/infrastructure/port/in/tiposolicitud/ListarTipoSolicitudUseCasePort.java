package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;


import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import java.util.List;

public interface ListarTipoSolicitudUseCasePort {
    
    List<TipoSolicitudDto> listarTiposSolicitud();
}
