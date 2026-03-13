package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.EditarTurnoAsignadoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;

import org.springframework.stereotype.Service;

@Service
public class EditarTurnoAsignadoUseCase implements EditarTurnoAsignadoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final TurnoRepositoryPort turnoRepository;
    private final AsignacionTurnoMapper asignacionTurnoMapper;

    public EditarTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository,
                                     UsuarioRepositoryPort usuarioRepository,
                                     TurnoRepositoryPort turnoRepository,
                                     AsignacionTurnoMapper asignacionTurnoMapper) {

        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
        this.asignacionTurnoMapper = asignacionTurnoMapper;
    }

    @Override
    public AsignacionTurnoDto editarTurnoAsignado(Long id, AsignacionTurnoDto dto) {

        AsignacionTurno asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));

        Long funcionarioId = dto.getFuncionarioId();
        Long turnoId = dto.getTurnoId();

        // Validar funcionario
        if (usuarioRepository.findById(funcionarioId).isEmpty()) {
            throw new IllegalArgumentException("Funcionario no encontrado");
        }

        // Validar turno
        if (turnoId != null && turnoRepository.findById(turnoId).isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        // Validar conflicto de fecha
        asignacionRepository.findByFuncionarioAndFecha(funcionarioId, dto.getFecha())
                .ifPresent(conflicto -> {
                    if (!conflicto.getId().equals(id)) {
                        throw new IllegalArgumentException("El funcionario ya tiene asignación en esa fecha");
                    }
                });

        // Actualizar datos
        asignacion.setFuncionarioId(funcionarioId);
        asignacion.setFecha(dto.getFecha());
        asignacion.setTurnoId(turnoId);

        AsignacionTurno actualizada = asignacionRepository.save(asignacion);

        return asignacionTurnoMapper.toDto(actualizada);
    }
}
