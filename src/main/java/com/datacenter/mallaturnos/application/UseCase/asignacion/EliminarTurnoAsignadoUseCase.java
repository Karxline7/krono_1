package com.datacenter.mallaturnos.application.asignacion;

import com.datacenter.mallaturnos.domain.port.out.AsignacionRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class EliminarTurnoAsignadoUseCase {

    private final AsignacionRepositoryPort asignacionRepository;

    public EliminarTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository) {
        this.asignacionRepository = asignacionRepository;
    }

    public boolean ejecutar(Long id) {
        
        if (!asignacionRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Asignación no encontrada");
        }

        asignacionRepository.delete(id);
        return true;
    }
}
