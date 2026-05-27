package com.datacenter.krono_12.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.krono_12.domain.model.AsignacionTurno;

@Component
public class AsignacionTurnoMapper {
    
    /**
     * Convierte AsignacionTurno (Domain) → AsignacionTurnoDto
     */
    public AsignacionTurnoDto toDto(AsignacionTurno asignacion) {
        if (asignacion == null) {
            return null;
        }
        
        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setId(asignacion.getId());
        dto.setFuncionarioId(asignacion.getFuncionarioId());
        dto.setTurnoId(asignacion.getTurnoId());
        dto.setFecha(asignacion.getFecha());
        
        return dto;
    }
    
    /**
     * Convierte AsignacionTurnoDto → AsignacionTurno (Domain)
     */
    public AsignacionTurno toDomain(AsignacionTurnoDto dto) {
        if (dto == null) {
            return null;
        }
        
        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setId(dto.getId());
        asignacion.setFuncionarioId(dto.getFuncionarioId());
        asignacion.setTurnoId(dto.getTurnoId());
        asignacion.setFecha(dto.getFecha());
        
        return asignacion;
    }
}
