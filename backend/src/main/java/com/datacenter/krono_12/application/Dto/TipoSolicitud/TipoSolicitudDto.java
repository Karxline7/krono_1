package com.datacenter.krono_12.application.Dto.TipoSolicitud;

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

    public String getNombre() {
        return nombre;
    }
}