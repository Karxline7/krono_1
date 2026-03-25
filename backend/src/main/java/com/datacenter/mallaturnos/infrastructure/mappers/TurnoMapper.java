package com.datacenter.mallaturnos.infrastructure.mappers;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.domain.model.Turno;

import org.springframework.stereotype.Component;

@Component
public class TurnoMapper {
    
    /**
     * Convierte Turno (Domain) → TurnoDto
     */
    public TurnoDto toDto(Turno turno) {
        if (turno == null) {
            return null;
        }
        
        TurnoDto dto = new TurnoDto();
        dto.setId(turno.getId());
        dto.setNombre(turno.getNombre());
        dto.setHoraInicio(turno.getHoraInicio());
        dto.setHoraFin(turno.getHoraFin());
        dto.setHoraalmuerzo(turno.getHoraalmuerzo());
        dto.setHorabreak(turno.getHorabreak());
        
        return dto;
    }
    
    /**
     * Convierte TurnoDto → Turno (Domain)
     */
    public Turno toDomain(TurnoDto dto) {
        if (dto == null) {
            return null;
        }
        
        Turno turno = new Turno();
        turno.setId(dto.getId());
        turno.setNombre(dto.getNombre());
        turno.setHoraInicio(dto.getHoraInicio());
        turno.setHoraFin(dto.getHoraFin());
        turno.setHoraalmuerzo(dto.getHoraalmuerzo());
        turno.setHorabreak(dto.getHorabreak());
        
        return turno;
    }
}
