package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.infrastructure.port.in.turno.CrearTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class CrearTurnoUseCase implements CrearTurnoUseCasePort {

    private final TurnoRepositoryPort turnoRepository;
    private final TurnoMapper turnoMapper;

    public CrearTurnoUseCase(TurnoRepositoryPort turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    @Override
    public TurnoDto crearTurno(TurnoDto dto) {

        // validar si el turno con el mismo nombre ya existe
        if (turnoRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un turno con el mismo nombre");
        }

        // Validar hora inicio < hora fin
        if (dto.getHoraInicio().isAfter(dto.getHoraFin()) || dto.getHoraInicio().equals(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
        }

        // Validar almuerzo dentro del turno
        if (dto.getHoraalmuerzo().isBefore(dto.getHoraInicio()) || dto.getHoraalmuerzo().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de almuerzo debe estar entre la hora de inicio y fin");
        }

        // Validar break dentro del turno
        if (dto.getHorabreak().isBefore(dto.getHoraInicio()) || dto.getHorabreak().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de break debe estar entre la hora de inicio y fin");
        }

        // Convertir DTO → Domain
        Turno nuevoTurno = turnoMapper.toDomain(dto);

        // Guardar
        Turno guardado = turnoRepository.save(nuevoTurno);

        // Convertir Domain → DTO
        return turnoMapper.toDto(guardado);
    }
}
