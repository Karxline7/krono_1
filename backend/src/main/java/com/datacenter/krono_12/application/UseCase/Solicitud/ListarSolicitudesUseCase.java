package com.datacenter.krono_12.application.UseCase.Solicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.SolicitudRepositoryPort;

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
