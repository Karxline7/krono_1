package com.datacenter.mallaturnos.application.Dto.Usuario;

import lombok.Data;
@Data
public class CrearUsuarioDto {

    private String nombre;
    private String tipoDocumento;
    private Integer numeroDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

}