package com.datacenter.mallaturnos.infrastructure.mappers;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

import org.springframework.stereotype.Component;

@Component
public class SolicitudTurnoMapper {
    
    /**
     * Convierte SolicitudTurno (Domain) → SolicitudTurnoDto
     */
    public SolicitudTurnoDto toDto(SolicitudTurno actualizada) {
        if (actualizada == null) {
            return null;
        }
        
        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setId(actualizada.getId());
        dto.setAsignacionTurnoId(actualizada.getAsignacionTurnoId());
        dto.setTipoSolicitudId(actualizada.getTipoSolicitudId());
        dto.setMotivoSolicitud(actualizada.getMotivoSolicitud());
        dto.setEstado(actualizada.getEstado());
        
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