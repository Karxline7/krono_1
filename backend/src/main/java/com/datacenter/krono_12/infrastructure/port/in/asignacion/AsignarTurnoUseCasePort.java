package com.datacenter.krono_12.infrastructure.port.in.asignacion;


import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;


public interface AsignarTurnoUseCasePort {

    AsignacionTurnoDto asignarTurno(AsignacionTurnoDto dto);
}