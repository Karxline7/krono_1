package com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Mapea a la tabla turnos se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "turnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private LocalTime horaalmuerzo;

    @Column(nullable = false)
    private LocalTime horabreak;
}
