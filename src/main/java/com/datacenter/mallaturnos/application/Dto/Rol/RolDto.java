package com.datacenter.mallaturnos.application.Dto.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
