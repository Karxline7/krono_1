package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.ObtenerUsuarioPorTurnoAsignadoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ObtenerUsuarioPorTurnoAsignadoUseCase implements ObtenerUsuarioPorTurnoAsignadoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerUsuarioPorTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository,
                                                 UsuarioRepositoryPort usuarioRepository) {
        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> obtenerUsuarioPorTurnoAsignado(Long funcionarioId, LocalDate fecha) {
        
        Optional<AsignacionTurno> asignacion = asignacionRepository.findByFuncionarioAndFecha(funcionarioId, fecha);
        
        if (!asignacion.isPresent()) {
            return Optional.empty();
        }

        return usuarioRepository.findById(asignacion.get().getFuncionarioId());
    }
}
