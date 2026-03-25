package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.ListarAsignacionesPorFechaUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;

import org.springframework.stereotype.Service;

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