package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerRolUseCase {

    private final RolRepositoryPort rolRepository;

    public ObtenerRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Rol> ejecutar(Long id) {
        return rolRepository.findById(id);
    }
}