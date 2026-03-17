package com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request;

import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import lombok.Data;

@Data
public class DenegarSolicitudRequest {

    private EstadoSolicitud Estado;
}
