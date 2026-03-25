package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;


import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.Request.EditarTurnoAsignadoRequest;


public interface EditarTurnoAsignadoUseCasePort {

    AsignacionTurnoDto editarTurnoAsignado(Long id, EditarTurnoAsignadoRequest dto);
}