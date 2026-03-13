package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.port.in.rol.CrearRolUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;


import org.springframework.stereotype.Service;

@Service
public class CrearRolUseCase implements CrearRolUseCasePort {

    private final RolRepositoryPort rolRepository;
    private final RolMapper rolMapper;

    public CrearRolUseCase(RolRepositoryPort rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public RolDto crearRol(RolDto dto) {

        if (rolRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Este rol ya existe");
        }

        Rol nuevoRol = rolMapper.toDomain(dto);

        Rol guardado = rolRepository.save(nuevoRol);

        return rolMapper.toDto(guardado);
    }
}