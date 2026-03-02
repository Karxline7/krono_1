package com.datacenter.mallaturnos.domain.model;


/**
 * Modelo de dominio: Representa un usuario del sistema.
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String tipodocumento;
    private Integer numerodocumento;
    private Integer contrasena;
    private RolType rol;
    private Long areaId;
    private Long cargoId;
    private Boolean activo;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con todos los parámetros
    public Usuario(Long id, String nombre, Integer numeroDocumento, 
                   String tipoDocumento, Integer contrasena, RolType rol, 
                    Long areaId, Long cargoId, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.tipodocumento = tipoDocumento;
        this.numerodocumento = numeroDocumento;
        this.contrasena = contrasena;
        this.rol = rol;
        this.areaId = areaId;
        this.cargoId = cargoId;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipodocumento() {
        return tipodocumento;
    }
    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }
    public Integer getNumerodocumento() {
        return numerodocumento;
    }
    public void setNumerodocumento(Integer numerodocumento) {
        this.numerodocumento = numerodocumento;
    }
    public Integer getContrasena() {
        return contrasena;
    }

    public void setContrasena(Integer contrasena) {
        this.contrasena = contrasena;
    }

    public RolType getRol() {
        return rol;
    }

    public void setRol(RolType rol) {
        this.rol = rol;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    public Long getCargoId() {
        return cargoId;
    }
    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
