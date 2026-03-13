package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.ListarAsignacionesPorFechaUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ListarAsignacionesPorFechaUseCase implements ListarAsignacionesPorFechaUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;

    public ListarAsignacionesPorFechaUseCase(AsignacionRepositoryPort asignacionRepository) {
        this.asignacionRepository = asignacionRepository;
    }

    @Override
    public List<AsignacionTurno> listarAsignacionesPorFecha(LocalDate fecha) {
        return asignacionRepository.findByFecha(fecha);
    }
}