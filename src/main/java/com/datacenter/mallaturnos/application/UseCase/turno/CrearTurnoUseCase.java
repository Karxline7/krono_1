package com.datacenter.mallaturnos.application.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.domain.port.out.TurnoRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * Use Case: Crear un nuevo turno
 */
@Service
public class CrearTurnoUseCase {

    private final TurnoRepositoryPort turnoRepository;

    public CrearTurnoUseCase(TurnoRepositoryPort turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    /**
     * Crea un nuevo turno
     * @param nombre Nombre del turno (ej: Turno Mañana)
     * @param horaInicio Hora de inicio (ej: 06:00)
     * @param horaFin Hora de fin (ej: 14:00)
     * @param  horaalmuerzo Hora de almuerzo (ej: 12:00)
     * @param horabreak Hora de break (ej: 10:00)
     * @return Turno creado
     */
    public Turno ejecutar(String nombre, LocalTime horaInicio, LocalTime horaFin, LocalTime horaalmuerzo, LocalTime horabreak) {
        
        // Validar que la hora de inicio sea menor que la de fin
        if (horaInicio.isAfter(horaFin) || horaInicio.equals(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
        }

        // Validar que la hora de almuerzo esté entre inicio y fin
        if (horaalmuerzo.isBefore(horaInicio) || horaalmuerzo.isAfter(horaFin)) {
            throw new IllegalArgumentException("La hora de almuerzo debe estar entre la hora de inicio y fin");
        }

        // Validar que la hora de break esté entre inicio y fin
        if (horabreak.isBefore(horaInicio) || horabreak.isAfter(horaFin)) {
            throw new IllegalArgumentException("La hora de break debe estar entre la hora de inicio y fin");
        }

        // Crear nuevo turno
        Turno nuevoTurno = new Turno();
        nuevoTurno.setNombre(nombre);
        nuevoTurno.setHoraInicio(horaInicio);
        nuevoTurno.setHoraFin(horaFin);
        nuevoTurno.setHoraalmuerzo(horaalmuerzo);
        nuevoTurno.setHorabreak(horabreak);

        // Guardar y retornar
        return turnoRepository.save(nuevoTurno);
    }
}
