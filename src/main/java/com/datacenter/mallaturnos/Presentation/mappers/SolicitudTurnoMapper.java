package com.datacenter.mallaturnos.Presentation.mappers;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.Presentation.Dto.SolicitudTurnoDto;
import org.springframework.stereotype.Component;

@Component
public class SolicitudTurnoMapper {
    
    /**
     * Convierte SolicitudTurno (Domain) → SolicitudTurnoDto
     */
    public SolicitudTurnoDto toDto(SolicitudTurno solicitud) {
        if (solicitud == null) {
            return null;
        }
        
        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setId(solicitud.getId());
        dto.setAsignacionTurnoId(solicitud.getAsignacionTurnoId());
        dto.setTipoSolicitudId(solicitud.getTipoSolicitudId());
        dto.setMotivoSolicitud(solicitud.getMotivoSolicitud());
        dto.setEstado(solicitud.getEstado());
        
        return dto;
    }
    
    /**
     * Convierte SolicitudTurnoDto → SolicitudTurno (Domain)
     */
    public SolicitudTurno toDomain(SolicitudTurnoDto dto) {
        if (dto == null) {
            return null;
        }
        
        SolicitudTurno solicitud = new SolicitudTurno();
        solicitud.setId(dto.getId());
        solicitud.setAsignacionTurnoId(dto.getAsignacionTurnoId());
        solicitud.setTipoSolicitudId(dto.getTipoSolicitudId());
        solicitud.setMotivoSolicitud(dto.getMotivoSolicitud());
        solicitud.setEstado(dto.getEstado());
        
        return solicitud;
    }
}