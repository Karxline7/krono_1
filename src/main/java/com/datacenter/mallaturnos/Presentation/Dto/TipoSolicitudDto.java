package com.datacenter.mallaturnos.Presentation.Dto;

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