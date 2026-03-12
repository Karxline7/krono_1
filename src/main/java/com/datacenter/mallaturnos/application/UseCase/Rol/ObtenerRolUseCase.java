package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.port.in.rol.ObtenerRolUseCasePort;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerRolUseCase implements ObtenerRolUseCasePort {

    private final RolRepositoryPort rolRepository;

    public ObtenerRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Rol> obtenerRol(Long id) {
        return rolRepository.findById(id);
    }
}