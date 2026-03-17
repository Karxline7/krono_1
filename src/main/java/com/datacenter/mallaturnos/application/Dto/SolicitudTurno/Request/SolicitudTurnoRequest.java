package com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request;

import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

import lombok.Data;
@Data
public class SolicitudTurnoRequest {
    private Long asignacionId;
    private Long tipoSolicitudId;
    private String motivoSolicitud;
    private EstadoSolicitud estado;
}
