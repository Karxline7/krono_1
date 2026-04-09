package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

import java.util.Optional;
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
