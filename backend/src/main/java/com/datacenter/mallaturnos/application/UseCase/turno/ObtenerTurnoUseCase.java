package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.infrastructure.port.in.turno.ObtenerTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TurnoMapper;

import org.springframework.stereotype.Service;

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
