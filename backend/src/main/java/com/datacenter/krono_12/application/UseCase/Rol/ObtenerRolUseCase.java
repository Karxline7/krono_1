package com.datacenter.krono_12.application.UseCase.Rol;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.in.rol.ObtenerRolUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

import java.util.Optional;

@Service
public class ObtenerRolUseCase implements ObtenerRolUseCasePort {

    private final RolRepositoryPort rolRepository;
    private final RolMapper rolMapper;

    public ObtenerRolUseCase(RolRepositoryPort rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    public Optional<RolDto> obtenerRol(Long id) {
        return rolRepository.findById(id).map(rolMapper::toDto);
    }
}