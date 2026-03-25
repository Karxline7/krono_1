package com.datacenter.mallaturnos.infrastructure.port.in.solicitud;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import java.util.List;

public interface ListarSolicitudesUseCasePort {
    List<SolicitudTurnoDto> listarSolicitudes(Long id);
}
