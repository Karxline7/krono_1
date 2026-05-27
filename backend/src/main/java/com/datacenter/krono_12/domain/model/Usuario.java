package com.datacenter.krono_12.domain.model;
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public Long getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(Long numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public Integer getContrasena() { return contrasena; }
    public void setContrasena(Integer contrasena) { this.contrasena = contrasena; }
    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }
    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }
    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }
}
