package com.datacenter.krono_12.application.Dto.SolicitudTurno.Request;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;

import lombok.Data;
@Data
public class SolicitudTurnoRequest {
    private Long asignacionId;
    private Long tipoSolicitudId;
    private String motivoSolicitud;
    private EstadoSolicitud estado;
}
