package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarSolicitudesUseCase {

    private final SolicitudRepositoryPort repository;
    private final SolicitudTurnoMapper mapper;

    public ListarSolicitudesUseCase(SolicitudRepositoryPort repository,
                                   SolicitudTurnoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SolicitudTurnoDto> listarSolicitudes() {
        return repository.obtener()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
