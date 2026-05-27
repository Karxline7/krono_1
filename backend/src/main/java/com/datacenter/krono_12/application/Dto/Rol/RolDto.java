package com.datacenter.krono_12.application.Dto.Rol;

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
