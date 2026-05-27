package com.datacenter.krono_12.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.infrastructure.mappers.AsignacionTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.in.asignacion.ListarAsignacionesPorFechaUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarAsignacionesPorFechaUseCase implements ListarAsignacionesPorFechaUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final AsignacionTurnoMapper asignacionTurnoMapper;

    public ListarAsignacionesPorFechaUseCase(AsignacionRepositoryPort asignacionRepository,
                                             AsignacionTurnoMapper asignacionTurnoMapper) {
        this.asignacionRepository = asignacionRepository;
        this.asignacionTurnoMapper = asignacionTurnoMapper;
    }

    @Override
    public List<AsignacionTurnoDto> listarAsignacionesPorFecha(LocalDate fecha) {

        List<AsignacionTurno> asignaciones = asignacionRepository.findByFecha(fecha);

        return asignaciones.stream()
                .map(asignacionTurnoMapper::toDto)
                .collect(Collectors.toList());
    }
}