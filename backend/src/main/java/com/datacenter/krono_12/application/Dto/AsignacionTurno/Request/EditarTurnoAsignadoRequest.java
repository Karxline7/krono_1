package com.datacenter.krono_12.application.Dto.AsignacionTurno.Request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EditarTurnoAsignadoRequest {
    
    private Long nuevoFuncionarioId;
    private Long nuevoTurnoId;
    private LocalDate nuevaFecha;
}
