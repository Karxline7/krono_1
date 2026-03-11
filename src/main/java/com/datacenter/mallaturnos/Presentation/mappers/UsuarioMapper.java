package com.datacenter.mallaturnos.Presentation.mappers;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.Presentation.Dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
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