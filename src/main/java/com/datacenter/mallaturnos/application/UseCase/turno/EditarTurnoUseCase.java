package com.datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.infrastructure.port.in.turno.EditarTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class EditarTurnoUseCase implements EditarTurnoUseCasePort {

    private final TurnoRepositoryPort turnoRepository;
    private final TurnoMapper turnoMapper;

    public EditarTurnoUseCase(TurnoRepositoryPort turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    @Override
    public TurnoDto editarTurno(Long id, TurnoDto dto) {

        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado con ID: " + id));

        // Validar hora inicio < hora fin
        if (dto.getHoraInicio().isAfter(dto.getHoraFin()) || dto.getHoraInicio().equals(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser menor que la hora de fin");
        }

        // Validar almuerzo dentro del turno
        if (dto.getHoraalmuerzo().isBefore(dto.getHoraInicio()) || dto.getHoraalmuerzo().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de almuerzo debe estar dentro del turno");
        }

        // Validar break dentro del turno
        if (dto.getHorabreak().isBefore(dto.getHoraInicio()) || dto.getHorabreak().isAfter(dto.getHoraFin())) {
            throw new IllegalArgumentException("La hora de break debe estar dentro del turno");
        }

        // Actualizar campos
        turno.setNombre(dto.getNombre());
        turno.setHoraInicio(dto.getHoraInicio());
        turno.setHoraFin(dto.getHoraFin());
        turno.setHoraalmuerzo(dto.getHoraalmuerzo());
        turno.setHorabreak(dto.getHorabreak());

        Turno actualizado = turnoRepository.save(turno);

        return turnoMapper.toDto(actualizado);
    }
}