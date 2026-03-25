package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.infrastructure.port.in.turno.ListarTurnosUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TurnoMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use Case: Listar todos los turnos
 * Los turnos son GLOBALES (no asociados a áreas)
 */
@Service
public class ListarTurnosUseCase implements ListarTurnosUseCasePort {

    private final TurnoRepositoryPort turnoRepository;
    private final TurnoMapper turnoMapper;

    public ListarTurnosUseCase(TurnoRepositoryPort turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    /**
     * Obtiene todos los turnos del sistema
     * @return Lista de todos los turnos
     */
    @Override
    public List<TurnoDto> listarTurnos() {
        return turnoRepository.findAll().stream()
                .map(turnoMapper::toDto)
                .collect(Collectors.toList());
    }
}
