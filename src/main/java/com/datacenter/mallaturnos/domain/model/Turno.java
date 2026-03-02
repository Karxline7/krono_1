package com.datacenter.mallaturnos.domain.model;

import java.time.LocalTime;

public class Turno {
    private Long id;
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaalmuerzo;
    private LocalTime horabreak;

    public Turno() {
    }

    public Turno(Long id, String nombre, LocalTime horaInicio, LocalTime horaFin,
                 LocalTime horaalmuerzo, LocalTime horabreak) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.horaalmuerzo = horaalmuerzo;
        this.horabreak = horabreak;
    }

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