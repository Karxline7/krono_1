package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import com.datacenter.mallaturnos.domain.model.RolType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Mapea a la tabla turnos se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(name = "tipo_documento", nullable = false)
    private String TipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private Integer NumeroDocumento;

    @Column(name = "contraseña", nullable = false)
    private Integer contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolType rol;

    @Column(name = "cargo_id", nullable = false)
    private Long cargoId;

    @Column(name = "area_id")
    private Long areaId;

    @Column(nullable = false)
    private Boolean activo;
}