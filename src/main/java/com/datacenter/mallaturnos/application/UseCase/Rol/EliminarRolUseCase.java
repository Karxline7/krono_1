package com.datacenter.mallaturnos.application.UseCase.Rol;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.infrastructure.port.in.rol.EliminarRolUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;

@Service
public class EliminarRolUseCase implements EliminarRolUseCasePort {

    private final RolRepositoryPort rolRepository;

    public EliminarRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public boolean eliminarRol(Long id) {
        
        if (!rolRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        rolRepository.delete(id);
        return true;
    }
}
