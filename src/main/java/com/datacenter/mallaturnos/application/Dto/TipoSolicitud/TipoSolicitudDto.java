package com.datacenter.mallaturnos.application.Dto.TipoSolicitud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSolicitudDto {
    private Long id;
    private String nombre;
    private String descripcion;
}