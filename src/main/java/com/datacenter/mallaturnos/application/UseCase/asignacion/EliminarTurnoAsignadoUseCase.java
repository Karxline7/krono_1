package com.datacenter.mallaturnos.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.port.in.asignacion.EliminarTurnoAsignadoUseCasePort;

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
