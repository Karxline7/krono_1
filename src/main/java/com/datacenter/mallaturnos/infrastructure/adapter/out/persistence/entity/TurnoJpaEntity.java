package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Entidad JPA: Mapea a la tabla turnos
 */
@Entity
@Table(name = "turnos")
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

    // Constructor vacío
    public TurnoJpaEntity() {
    }

    // Constructor con todos los parámetros
    public TurnoJpaEntity(Long id, String nombre, LocalTime horaInicio, LocalTime horaFin,
                          LocalTime horaalmuerzo, LocalTime horabreak) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.horaalmuerzo = horaalmuerzo;
        this.horabreak = horabreak;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public LocalTime getHoraalmuerzo() { return horaalmuerzo; }
    public void setHoraalmuerzo(LocalTime horaalmuerzo) { this.horaalmuerzo = horaalmuerzo; }

    public LocalTime getHorabreak() { return horabreak; }
    public void setHorabreak(LocalTime horabreak) { this.horabreak = horabreak; }
}
