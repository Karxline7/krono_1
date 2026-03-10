package com.datacenter.mallaturnos.Presentation.mappers;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.Presentation.Dto.TipoSolicitudDto;
import org.springframework.stereotype.Component;

@Component
public class TipoSolicitudMapper {
    
    /**
     * Convierte TipoSolicitud (Domain) → TipoSolicitudDto
     */
    public TipoSolicitudDto toDto(TipoSolicitud tipoSolicitud) {
        if (tipoSolicitud == null) {
            return null;
        }
        
        TipoSolicitudDto dto = new TipoSolicitudDto();
        dto.setId(tipoSolicitud.getId());
        dto.setNombre(tipoSolicitud.getNombre());
        dto.setDescripcion(tipoSolicitud.getDescripcion());

        return dto;
    }
    
    /**
     * Convierte TipoSolicitudDto → TipoSolicitud (Domain)
     */
    public TipoSolicitud toDomain(TipoSolicitudDto dto) {
        if (dto == null) {
            return null;
        }
        
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setId(dto.getId());
        tipoSolicitud.setNombre(dto.getNombre());
        tipoSolicitud.setDescripcion(dto.getDescripcion());
        
        return tipoSolicitud;
    }
}
