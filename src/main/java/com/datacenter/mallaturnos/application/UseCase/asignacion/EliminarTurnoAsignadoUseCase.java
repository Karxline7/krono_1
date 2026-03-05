package com.datacenter.mallaturnos.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;

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
