package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.port.in.asignacion.ObtenerUsuarioPorTurnoAsignadoUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.infrastructure.mappers.UsuarioMapper;

import org.springframework.stereotype.Service;

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
