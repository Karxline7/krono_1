package com.datacenter.mallaturnos.application.Dto.Usuario;

import lombok.Data;
@Data
public class EditarUsuarioDto {

    private Long id;
    private String nombre;
    private String tipoDocumento;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

}