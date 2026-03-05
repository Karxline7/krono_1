package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Obtener un turno por ID
 */
@Service
public class ObtenerTurnoUseCase {

    private final TurnoRepositoryPort turnoRepository;

    public ObtenerTurnoUseCase(TurnoRepositoryPort turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    /**
     * Obtiene un turno por su ID
     * @param id ID del turno
     * @return Optional con el turno si existe
     */
    public Optional<Turno> ejecutar(Long id) {
        return turnoRepository.findById(id);
    }
}
