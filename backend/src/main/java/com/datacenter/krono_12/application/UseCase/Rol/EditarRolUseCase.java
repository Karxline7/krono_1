package com.datacenter.krono_12.application.UseCase.Rol;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.in.rol.EditarRolUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;


@Service
public class EditarRolUseCase implements EditarRolUseCasePort {

    private final RolRepositoryPort rolRepository;
    private final RolMapper rolMapper;

    public EditarRolUseCase(RolRepositoryPort rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    @Override
    public RolDto editarRol(Long id, RolDto dto) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());

        Rol actualizado = rolRepository.save(rol);

        return rolMapper.toDto(actualizado);
    }
}