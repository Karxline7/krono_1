package com.datacenter.krono_12.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.infrastructure.port.in.asignacion.EliminarTurnoAsignadoUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;

@Service
public class EliminarTurnoAsignadoUseCase implements EliminarTurnoAsignadoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;

    public EliminarTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository) {
        this.asignacionRepository = asignacionRepository;
    }

    public boolean eliminarTurnoAsignado(Long id) {
        
        if (!asignacionRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Asignación no encontrada");
        }

        asignacionRepository.delete(id);
        return true;
    }
}
