package com.datacenter.krono_12.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTurno {
    private Long id;
    private Long funcionarioId;  
    private Long turnoId;
    private LocalDate fecha;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }
    public Long getTurnoId() { return turnoId; }
    public void setTurnoId(Long turnoId) { this.turnoId = turnoId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}