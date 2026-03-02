package com.datacenter.mallaturnos.domain.port.out;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de solicitudes de turno.
 */
public interface SolicitudRepositoryPort {
    Optional<SolicitudTurno> findById(Long id);
    boolean existsDenegadaForAsignacion(Long asignacionId);
    boolean existsPendienteForAsignacion(Long asignacionId);
    boolean existsAprobadaForAsignacion(Long asignacionId);
    SolicitudTurno save(SolicitudTurno solicitud);
}
