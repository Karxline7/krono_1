package com.datacenter.mallaturnos.domain.model;
/**
 * Modelo de dominio: Representa un usuario del sistema.
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String tipoDocumento;
    private Long numeroDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;
}
