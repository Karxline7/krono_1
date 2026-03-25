package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.AsignarTurnoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class AsignarTurnoUseCase implements AsignarTurnoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final TurnoRepositoryPort turnoRepository;
    private final AsignacionTurnoMapper asignacionTurnoMapper;

    public AsignarTurnoUseCase(AsignacionRepositoryPort asignacionRepository,
                              UsuarioRepositoryPort usuarioRepository,
                              TurnoRepositoryPort turnoRepository,
                              AsignacionTurnoMapper asignacionTurnoMapper) {

        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
        this.asignacionTurnoMapper = asignacionTurnoMapper;
    }

    @Override
    public AsignacionTurnoDto asignarTurno(AsignacionTurnoDto dto) {

        Long funcionarioId = dto.getFuncionarioId();
        Long turnoId = dto.getTurnoId();

        if (usuarioRepository.findById(funcionarioId).isEmpty()) {
            throw new IllegalArgumentException("Funcionario no encontrado");
        }

        if (turnoId != null && turnoRepository.findById(turnoId).isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        if (asignacionRepository.existsByFuncionarioAndFecha(funcionarioId, dto.getFecha())) {
            throw new IllegalArgumentException("El funcionario ya tiene asignación en esa fecha");
        }

        AsignacionTurno nuevaAsignacion = asignacionTurnoMapper.toDomain(dto);

        AsignacionTurno guardada = asignacionRepository.save(nuevaAsignacion);

        return asignacionTurnoMapper.toDto(guardada);
    }
}