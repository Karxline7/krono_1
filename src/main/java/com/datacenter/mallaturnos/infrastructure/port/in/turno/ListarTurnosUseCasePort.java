package com.datacenter.mallaturnos.infrastructure.port.in.turno;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import java.util.List;

public interface ListarTurnosUseCasePort {

    List<TurnoDto> listarTurnos();
}
