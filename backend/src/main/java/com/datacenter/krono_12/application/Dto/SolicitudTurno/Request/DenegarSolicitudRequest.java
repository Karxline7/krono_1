package com.datacenter.krono_12.application.Dto.SolicitudTurno.Request;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;

import lombok.Data;

@Data
public class DenegarSolicitudRequest {

    private EstadoSolicitud Estado;
}
