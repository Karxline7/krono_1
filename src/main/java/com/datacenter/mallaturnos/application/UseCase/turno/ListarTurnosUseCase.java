package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.port.in.turno.ListarTurnosUseCasePort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use Case: Listar todos los turnos
 * Los turnos son GLOBALES (no asociados a áreas)
 */
@Service
public class ListarTurnosUseCase implements ListarTurnosUseCasePort {

    private final TurnoRepositoryPort turnoRepository;

    public ListarTurnosUseCase(TurnoRepositoryPort turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    /**
     * Obtiene todos los turnos del sistema
     * @return Lista de todos los turnos
     */
    @Override
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }
}
