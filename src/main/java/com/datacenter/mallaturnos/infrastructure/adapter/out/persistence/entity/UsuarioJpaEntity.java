package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import com.datacenter.mallaturnos.domain.model.RolType;
import jakarta.persistence.*;

/**
 * Entidad JPA: Mapea a la tabla usuarios
 */
@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false)
    private Integer numeroDocumento;

    @Column(nullable = false)
    private Integer contraseña;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolType rol;

    @Column(nullable = false)
    private Long cargoId;

    @Column(name = "area_id")
    private Long areaId;

    @Column(nullable = false)
    private Boolean activo;

    // Constructor vacío
    public UsuarioJpaEntity() {
    }

    // Constructor con todos los parámetros
    public UsuarioJpaEntity(Long id, String nombres, String tipoDocumento, Integer numeroDocumento, 
                            Integer contraseña, RolType rol, Long cargoId, Long areaId, Boolean activo) {
        this.id = id;
        this.nombres = nombres;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.contraseña = contraseña;
        this.rol = rol;
        this.cargoId = cargoId;
        this.areaId = areaId;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public Integer getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(Integer numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public Integer getContraseña() { return contraseña; }
    public void setContraseña(Integer contraseña) { this.contraseña = contraseña; }

    public RolType getRol() { return rol; }
    public void setRol(RolType rol) { this.rol = rol; }

    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }

    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}