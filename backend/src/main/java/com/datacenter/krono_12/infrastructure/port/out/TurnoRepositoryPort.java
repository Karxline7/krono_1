package com.datacenter.krono_12.infrastructure.port.out;

import java.util.List;
import java.util.Optional;

import com.datacenter.krono_12.domain.model.Turno;

/**
 * Puerto de salida: Define el contrato para persistencia de turnos.
 */
public interface TurnoRepositoryPort {
    Optional<Turno> findById(Long id);
    Turno save(Turno turno);
    void delete(Long id);
    List<Turno> findAll();
    Optional<Turno> findByNombre(String nombre);
}