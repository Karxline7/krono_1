package com.datacenter.krono_12.infrastructure.port.in.asignacion;

import java.time.LocalDate;
import java.util.Optional;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;

public interface ObtenerUsuarioPorTurnoAsignadoUseCasePort {

    Optional<UsuarioDto> obtenerUsuarioPorTurnoAsignado(Long funcionarioId, LocalDate fecha);
}