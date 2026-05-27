package com.datacenter.krono_12.application.Dto.SolicitudTurno;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudTurnoDto {
    private Long id;
    private Long asignacionTurnoId;
    private Long tipoSolicitudId;
    private String motivoSolicitud;
    private EstadoSolicitud estado;
}