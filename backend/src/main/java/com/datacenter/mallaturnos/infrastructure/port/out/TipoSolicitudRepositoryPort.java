package com.datacenter.mallaturnos.infrastructure.port.out;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Define el contrato para persistencia de tipos de solicitud
 */
public interface TipoSolicitudRepositoryPort {
    Optional<TipoSolicitud> findById(Long id);
    Optional<TipoSolicitud> findByNombre(String nombre);
    List<TipoSolicitud> findAll();
    TipoSolicitud save(TipoSolicitud tipoSolicitud);
    void delete(Long id);
}