package com.datacenter.mallaturnos.application.Dto.AsignacionTurno.Request;

import lombok.Data;
import java.time.LocalDate;
@Data
public class AsignarTurnoRequest {
    
    private Long funcionarioId;
    private Long turnoId;
    private Long areaId;
    private LocalDate fecha;
}
