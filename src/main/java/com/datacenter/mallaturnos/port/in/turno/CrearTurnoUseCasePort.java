package com.datacenter.mallaturnos.port.in.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import java.time.LocalTime;

public interface CrearTurnoUseCasePort {

    Turno crearTurno(String nombre,
                     LocalTime horaInicio,
                     LocalTime horaFin,
                     LocalTime horaalmuerzo,
                     LocalTime horabreak);
}
