package com.datacenter.mallaturnos.application.Dto.SolicitudTurno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

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