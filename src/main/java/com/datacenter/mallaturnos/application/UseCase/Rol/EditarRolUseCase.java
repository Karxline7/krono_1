package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.port.in.rol.EditarRolUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;

import org.springframework.stereotype.Service;


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