package com.datacenter.mallaturnos.Presentation.mappers;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.Presentation.Dto.RolDto;
import org.springframework.stereotype.Component;

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
