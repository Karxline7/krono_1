package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.port.in.asignacion.AsignarTurnoUseCasePort;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AsignarTurnoUseCase implements AsignarTurnoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final TurnoRepositoryPort turnoRepository;

    public AsignarTurnoUseCase(AsignacionRepositoryPort asignacionRepository,
                              UsuarioRepositoryPort usuarioRepository,
                              TurnoRepositoryPort turnoRepository) {
        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
    }

    public AsignacionTurno asignarTurno(Long funcionarioId, Long turnoId,
                                       LocalDate fecha) {

        if (!usuarioRepository.findById(funcionarioId).isPresent()) {
            throw new IllegalArgumentException("Funcionario no encontrado");
        }

        if (turnoId != null && !turnoRepository.findById(turnoId).isPresent()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        if (asignacionRepository.existsByFuncionarioAndFecha(funcionarioId, fecha)) {
            throw new IllegalArgumentException("El funcionario ya tiene asignación en esa fecha");
        }

        AsignacionTurno nuevaAsignacion = new AsignacionTurno();
        nuevaAsignacion.setFuncionarioId(funcionarioId);
        nuevaAsignacion.setTurnoId(turnoId);
        nuevaAsignacion.setFecha(fecha);

        return asignacionRepository.save(nuevaAsignacion);
    }
}
