package com.datacenter.krono_12.application.UseCase.Rol;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.in.rol.CrearRolUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

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