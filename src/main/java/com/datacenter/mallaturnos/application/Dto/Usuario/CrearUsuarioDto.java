package com.datacenter.mallaturnos.application.Dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDto {

    private String nombre;
    private String tipoDocumento;
    private Long numeroDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

}