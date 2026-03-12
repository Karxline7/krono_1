package com.datacenter.mallaturnos.port.in.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;

public interface CrearTipoSolicitudUseCasePort {

    TipoSolicitud crearTipoSolicitud(String nombre, String descripcion);
}
