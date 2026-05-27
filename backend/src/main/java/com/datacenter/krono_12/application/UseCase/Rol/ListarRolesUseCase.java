package com.datacenter.krono_12.application.UseCase.Rol;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.in.rol.ListarRolesUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

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
