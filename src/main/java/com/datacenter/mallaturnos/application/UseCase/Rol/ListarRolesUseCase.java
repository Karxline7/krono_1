package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.infrastructure.port.in.rol.ListarRolesUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarRolesUseCase implements ListarRolesUseCasePort {

    private final RolRepositoryPort rolRepository;
    private final RolMapper rolMapper;

    public ListarRolesUseCase(RolRepositoryPort rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public List<RolDto> listarRoles() {

        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toDto)
                .toList();
    }
}
