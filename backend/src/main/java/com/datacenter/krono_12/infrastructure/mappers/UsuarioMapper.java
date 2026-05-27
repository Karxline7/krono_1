package com.datacenter.krono_12.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.CargoJpaRepository;

@Component
public class UsuarioMapper {

    private final CargoJpaRepository cargoRepository;

    public UsuarioMapper(CargoJpaRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    /**
     * Convierte Usuario (Domain) → UsuarioDto
     */
    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setTipoDocumento(usuario.getTipoDocumento());
        dto.setNumeroDocumento(usuario.getNumeroDocumento());
        dto.setRolId(usuario.getRolId());
        dto.setCargoId(usuario.getCargoId());
        if (usuario.getCargoId() != null) {
            String cargoNombre = cargoRepository.findNombreById(usuario.getCargoId());
            dto.setCargoNombre(cargoNombre);
        }
        dto.setAreaId(usuario.getAreaId());

        return dto;
    }

    /**
     * Convierte UsuarioDto → Usuario (Domain)
     */
    public Usuario toDomain(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setTipoDocumento(dto.getTipoDocumento());
        usuario.setNumeroDocumento(dto.getNumeroDocumento());
        usuario.setRolId(dto.getRolId());
        usuario.setCargoId(dto.getCargoId());
        usuario.setAreaId(dto.getAreaId());

        return usuario;
    }
}