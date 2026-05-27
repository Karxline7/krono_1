package com.datacenter.krono_12.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.krono_12.application.Dto.AsignacionTurno.Request.EditarTurnoAsignadoRequest;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.infrastructure.mappers.AsignacionTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.in.asignacion.EditarTurnoAsignadoUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

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
    public AsignacionTurnoDto editarTurnoAsignado(Long id, EditarTurnoAsignadoRequest dto) {

        AsignacionTurno asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));

        Long funcionarioId = dto.getNuevoFuncionarioId();
        Long turnoId = dto.getNuevoTurnoId();

        // Validar funcionario
        if (usuarioRepository.findById(funcionarioId).isEmpty()) {
            throw new IllegalArgumentException("Funcionario no encontrado");
        }

        // Validar turno
        if (turnoId != null && turnoRepository.findById(turnoId).isEmpty()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        // Validar conflicto de fecha
        asignacionRepository.findByFuncionarioAndFecha(funcionarioId, dto.getNuevaFecha())
                .ifPresent(conflicto -> {
                    if (!conflicto.getId().equals(id)) {
                        throw new IllegalArgumentException("El funcionario ya tiene asignación en esa fecha");
                    }
                });

        // Actualizar datos
        asignacion.setFuncionarioId(funcionarioId);
        asignacion.setFecha(dto.getNuevaFecha());
        asignacion.setTurnoId(turnoId);

        AsignacionTurno actualizada = asignacionRepository.save(asignacion);

        return asignacionTurnoMapper.toDto(actualizada);
    }
}
