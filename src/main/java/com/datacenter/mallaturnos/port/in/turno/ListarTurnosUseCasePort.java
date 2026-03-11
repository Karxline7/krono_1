package com.datacenter.mallaturnos.port.in.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import java.util.List;

public interface ListarTurnosUseCasePort {

    List<Turno> listarTurnos();
}
