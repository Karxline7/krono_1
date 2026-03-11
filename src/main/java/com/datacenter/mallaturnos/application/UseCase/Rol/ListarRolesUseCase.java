package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.port.out.RolRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarRolesUseCase {

    private final RolRepositoryPort rolRepository;

    public ListarRolesUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> ejecutar() {
        return rolRepository.findAll();
    }
}
