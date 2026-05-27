package com.datacenter.krono_12.application.Dto.Turno.Request;

import lombok.Data;
import java.time.LocalTime;

@Data
public class EditarTurnoRequest {
    
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaalmuerzo;
    private LocalTime horabreak;
}
