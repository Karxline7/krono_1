package com.datacenter.mallaturnos.application.Dto.Usuario.Request;

import lombok.Data;

@Data
public class EditarUsuarioRequest {

    private String nombre;
    private String tipoDocumento;
    private String contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

}