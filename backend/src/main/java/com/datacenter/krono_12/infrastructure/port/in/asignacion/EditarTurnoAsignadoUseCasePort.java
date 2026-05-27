package com.datacenter.krono_12.infrastructure.port.in.asignacion;


import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.krono_12.application.Dto.AsignacionTurno.Request.EditarTurnoAsignadoRequest;


public interface EditarTurnoAsignadoUseCasePort {

    AsignacionTurnoDto editarTurnoAsignado(Long id, EditarTurnoAsignadoRequest dto);
}