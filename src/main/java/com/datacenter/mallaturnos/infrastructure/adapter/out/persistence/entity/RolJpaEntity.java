package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Mapea a la tabla rol, se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "Rol")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RolJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;
}