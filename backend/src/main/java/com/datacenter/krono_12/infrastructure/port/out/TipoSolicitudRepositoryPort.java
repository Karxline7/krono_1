package com.datacenter.krono_12.infrastructure.port.out;

import java.util.List;
import java.util.Optional;

import com.datacenter.krono_12.domain.model.TipoSolicitud;

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