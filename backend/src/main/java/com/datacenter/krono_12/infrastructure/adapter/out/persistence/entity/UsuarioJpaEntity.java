package com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Mapea a la tabla usuario se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private Long numeroDocumento;

    @Column(name = "contrasena", nullable = false)
    private Integer contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RolJpaEntity rol;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private CargoJpaEntity cargo;

    @Column(name = "area_id")
    private Long areaId;
}