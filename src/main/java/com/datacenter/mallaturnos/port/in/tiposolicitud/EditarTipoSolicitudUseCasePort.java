package com.datacenter.mallaturnos.port.in.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;

public interface EditarTipoSolicitudUseCasePort {

    TipoSolicitud editarTipoSolicitud(Long id, String nombre, String descripcion);
}
