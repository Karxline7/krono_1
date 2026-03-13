package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;


import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;


public interface EditarTurnoAsignadoUseCasePort {

    AsignacionTurnoDto editarTurnoAsignado(Long id, AsignacionTurnoDto dto);
}