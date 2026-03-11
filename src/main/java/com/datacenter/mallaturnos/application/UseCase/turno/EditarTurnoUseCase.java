package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.port.out.TurnoRepositoryPort;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Use Case: Editar un turno existente
 */
@Service
public class EditarTurnoUseCase {

    private final TurnoRepositoryPort turnoRepository;

    public EditarTurnoUseCase(TurnoRepositoryPort turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    /**
     * Edita un turno existente
     * @param id ID del turno a editar
     * @param nombre Nuevo nombre
     * @param horaInicio Nueva hora de inicio
     * @param horaFin Nueva hora de fin
     * @param horaalmuerzo Nueva hora de almuerzo
     * @param horabreak Nueva hora de break
     * @return Turno editado
     */
    public Turno ejecutar(Long id, String nombre, LocalTime horaInicio, 
                          LocalTime horaFin, LocalTime horaalmuerzo, LocalTime horabreak) {
        
        // Obtener turno existente
        Optional<Turno> turnoExistente = turnoRepository.findById(id);
        
        if (!turnoExistente.isPresent()) {
            throw new IllegalArgumentException("Turno no encontrado con ID: " + id);
        }

        // Validar que la hora de inicio sea menor que la de fin
        if (horaInicio.isAfter(horaFin) || horaInicio.equals(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser menor que la hora de fin");
        }

        Turno turno = turnoExistente.get();

        // Actualizar campos
        turno.setNombre(nombre);
        turno.setHoraInicio(horaInicio);
        turno.setHoraFin(horaFin);
        turno.setHoraalmuerzo(horaalmuerzo);
        turno.setHorabreak(horabreak);

        // Guardar y retornar
        return turnoRepository.save(turno);
    }
}