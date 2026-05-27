package com.datacenter.krono_12.application.UseCase.asignacion;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.in.asignacion.ObtenerUsuarioPorTurnoAsignadoUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ObtenerUsuarioPorTurnoAsignadoUseCase implements ObtenerUsuarioPorTurnoAsignadoUseCasePort {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ObtenerUsuarioPorTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository,
                                                 UsuarioRepositoryPort usuarioRepository,
                                                 UsuarioMapper usuarioMapper) {
        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Optional<UsuarioDto> obtenerUsuarioPorTurnoAsignado(Long funcionarioId, LocalDate fecha) {
        
        Optional<AsignacionTurno> asignacion = asignacionRepository.findByFuncionarioAndFecha(funcionarioId, fecha);
        
        if (!asignacion.isPresent()) {
            return Optional.empty();
        }

        return usuarioRepository.findById(asignacion.get().getFuncionarioId())
                .map(usuarioMapper::toDto);
    }
}
