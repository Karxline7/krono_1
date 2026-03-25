package com.datacenter.mallaturnos.infrastructure.port.in.asignacion;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;

import java.time.LocalDate;
import java.util.Optional;

public interface ObtenerUsuarioPorTurnoAsignadoUseCasePort {

    Optional<UsuarioDto> obtenerUsuarioPorTurnoAsignado(Long funcionarioId, LocalDate fecha);
}