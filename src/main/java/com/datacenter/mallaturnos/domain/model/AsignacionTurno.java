package com.datacenter.mallaturnos.domain.model;

import java.time.LocalDate;

public class AsignacionTurno {

    private Long id;
    private Long funcionarioId;  
    private Long turnoId;
    private LocalDate fecha;

    public AsignacionTurno() {
    }

    public AsignacionTurno(Long id,
                           Long funcionarioId,
                           Long turnoId,
                           LocalDate fecha) {
        this.id = id;
        this.funcionarioId = funcionarioId;
        this.turnoId = turnoId;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public Long getTurnoId() { return turnoId; }
    public void setTurnoId(Long turnoId) { this.turnoId = turnoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}