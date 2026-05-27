package com.datacenter.krono_12.application.UseCase.turno;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;
import com.datacenter.krono_12.infrastructure.port.in.turno.ObtenerTurnoUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.util.Optional;

/**
 * Use Case: Obtener un turno por ID
 */
@Service
public class ObtenerTurnoUseCase implements ObtenerTurnoUseCasePort {

    private final TurnoRepositoryPort turnoRepository;
    private final TurnoMapper turnoMapper;

    public ObtenerTurnoUseCase(TurnoRepositoryPort turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    /**
     * Obtiene un turno por su ID
     * @param id ID del turno
     * @return Optional con el turno si existe
     */
    @Override
    public Optional<TurnoDto> obtenerTurno(Long id) {
        return turnoRepository.findById(id).map(turnoMapper::toDto);
    }
}
