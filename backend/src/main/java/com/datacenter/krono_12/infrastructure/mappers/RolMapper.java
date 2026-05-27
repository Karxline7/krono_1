package com.datacenter.krono_12.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.domain.model.Rol;

@Component
public class RolMapper {
    
    /**
     * Convierte Rol (Domain) → RolDto
     */
    public RolDto toDto(Rol rol) {
        if (rol == null) {
            return null;
        }
        
        RolDto dto = new RolDto();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        
        return dto;
    }
    
    /**
     * Convierte RolDto → Rol (Domain)
     */
    public Rol toDomain(RolDto dto) {
        if (dto == null) {
            return null;
        }
        
        Rol rol = new Rol();
        rol.setId(dto.getId());
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        
        return rol;
    }
}
