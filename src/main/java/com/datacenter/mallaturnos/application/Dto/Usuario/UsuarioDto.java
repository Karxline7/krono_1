package com.datacenter.mallaturnos.application.Dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String tipoDocumento;
    private Long numeroDocumento;
    private Long rolId;                                                              
    private Long cargoId;
    private String cargoNombre;
    private Long areaId;
}
