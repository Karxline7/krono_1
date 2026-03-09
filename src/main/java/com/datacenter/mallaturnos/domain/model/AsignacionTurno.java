package com.datacenter.mallaturnos.domain.model;

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
}