package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.infrastructure.port.in.rol.ObtenerRolUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;

import org.springframework.stereotype.Service;

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