package com.datacenter.mallaturnos.port.out;

import com.datacenter.mallaturnos.domain.model.Turno;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de turnos.
 */
public interface TurnoRepositoryPort {
    Optional<Turno> findById(Long id);
    Turno save(Turno turno);
    void delete(Long id);
    List<Turno> findAll();
}