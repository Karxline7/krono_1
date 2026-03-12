package com.datacenter.mallaturnos.port.out;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de asignaciones de turno.
 */
public interface AsignacionRepositoryPort {
    Optional<AsignacionTurno> findById(Long id);
    Optional<AsignacionTurno> findByFuncionarioAndFecha(Long funcionarioId, LocalDate fecha);
    boolean existsByFuncionarioAndFecha(Long funcionarioId, LocalDate fecha);
    List<AsignacionTurno> findByAreaAndPeriodo(Long areaId, LocalDate inicio, LocalDate fin);
    List<AsignacionTurno> findByFecha(LocalDate fecha);
    List<AsignacionTurno> findByFuncionarioAndPeriodo(Long funcionarioId, LocalDate inicio, LocalDate fin);
    AsignacionTurno save(AsignacionTurno asignacion);
    void delete(Long id);
}
