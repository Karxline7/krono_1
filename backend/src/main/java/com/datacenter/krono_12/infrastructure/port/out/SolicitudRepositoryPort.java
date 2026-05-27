package com.datacenter.krono_12.infrastructure.port.out;

import java.util.Optional;

import com.datacenter.krono_12.domain.model.SolicitudTurno;

import java.util.List;

/**
 * Puerto de salida: Define el contrato para persistencia de solicitudes de turno.
 */
public interface SolicitudRepositoryPort {
    Optional<SolicitudTurno> findById(Long id);
    boolean existsDenegadaForAsignacion(Long asignacionId);
    boolean existsPendienteForAsignacion(Long asignacionId);
    boolean existsAprobadaForAsignacion(Long asignacionId);
    SolicitudTurno save(SolicitudTurno solicitud);
    List<SolicitudTurno> obtener();
    boolean existsById(Long id);
    void deleteById(Long id);
}
