package com.datacenter.krono_12.infrastructure.port.in.turno;

import java.util.List;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;

public interface ListarTurnosUseCasePort {

    List<TurnoDto> listarTurnos();
}
