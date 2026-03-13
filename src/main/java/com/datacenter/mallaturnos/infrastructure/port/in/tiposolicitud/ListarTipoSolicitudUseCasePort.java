package com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud;


import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import java.util.List;

public interface ListarTipoSolicitudUseCasePort {
    
    List<TipoSolicitud> listarTiposSolicitud();
}
