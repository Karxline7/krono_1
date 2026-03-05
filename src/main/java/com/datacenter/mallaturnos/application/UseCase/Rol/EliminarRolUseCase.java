package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class EliminarRolUseCase {

    private final RolRepositoryPort rolRepository;

    public EliminarRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public boolean ejecutar(Long id) {
        
        if (!rolRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        rolRepository.delete(id);
        return true;
    }
}
