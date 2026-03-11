package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.port.in.turno.EliminarTurnoUseCasePort;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Eliminar un turno
 */
@Service
public class EliminarTurnoUseCase implements EliminarTurnoUseCasePort {

    private final TurnoRepositoryPort turnoRepository;

    public EliminarTurnoUseCase(TurnoRepositoryPort turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    /**
     * Elimina un turno de la BD (hard delete)
     * @param id ID del turno a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarTurno(Long id) {
        
        // Verificar que el turno existe
        Optional<Turno> turnoExistente = turnoRepository.findById(id);
        
        if (!turnoExistente.isPresent()) {
            throw new IllegalArgumentException("Turno no encontrado con ID: " + id);
        }

        // Eliminar turno
        turnoRepository.delete(id);
        
        return true;
    }
}