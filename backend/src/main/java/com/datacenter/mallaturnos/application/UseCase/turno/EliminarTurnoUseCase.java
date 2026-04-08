package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.infrastructure.port.in.turno.EliminarTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use Case: Eliminar un turno
 */
@Service
public class EliminarTurnoUseCase implements EliminarTurnoUseCasePort {

    private final TurnoRepositoryPort turnoRepository;
    private final AsignacionRepositoryPort asignacionRepository;

    public EliminarTurnoUseCase(TurnoRepositoryPort turnoRepository, AsignacionRepositoryPort asignacionRepository) {
        this.turnoRepository = turnoRepository;
        this.asignacionRepository = asignacionRepository;
    }

    /**
     * Elimina un turno de la BD (hard delete)
     * @param id ID del turno a eliminar
     * @return true si se eliminó exitosamente
     */
    @Transactional
    public boolean eliminarTurno(Long id) {

        Optional<Turno> turnoExistente = turnoRepository.findById(id);

        if (turnoExistente.isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado con ID: " + id);
        }

        // 🔥 1. borrar asignaciones de ese turno
        asignacionRepository.deleteByTurnoId(id);

        // 🔥 2. borrar turno
        turnoRepository.delete(id);

        return true;
    }


}