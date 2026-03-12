package com.datacenter.mallaturnos.port.in.asignacion;

import com.datacenter.mallaturnos.domain.model.Usuario;
import java.time.LocalDate;
import java.util.Optional;

public interface ObtenerUsuarioPorTurnoAsignadoUseCasePort {

    Optional<Usuario> obtenerUsuarioPorTurnoAsignado(Long funcionarioId, LocalDate fecha);
}