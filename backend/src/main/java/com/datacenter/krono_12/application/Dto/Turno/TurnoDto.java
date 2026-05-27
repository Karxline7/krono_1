package com.datacenter.krono_12.application.Dto.Turno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDto {
    private Long id;
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaalmuerzo;
    private LocalTime horabreak;
}