package com.datacenter.krono_12.infrastructure.adapter.out.persistence.adapter;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.TipoSolicitudJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.TipoSolicitudJpaRepository;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto TipoSolicitudRepositoryPort
 */
@Component
public class TipoSolicitudPersistenceAdapter implements TipoSolicitudRepositoryPort {

    private final TipoSolicitudJpaRepository jpaRepository;

    public TipoSolicitudPersistenceAdapter(TipoSolicitudJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<TipoSolicitud> findById(Long id) {
    return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<TipoSolicitud> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).map(this::toDomain);
    }

    @Override
    public List<TipoSolicitud> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TipoSolicitud save(TipoSolicitud tipoSolicitud) {
        TipoSolicitudJpaEntity entity = toEntity(tipoSolicitud);
        TipoSolicitudJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private TipoSolicitud toDomain(TipoSolicitudJpaEntity entity) {
        return new TipoSolicitud(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion()
        );
    }

    private TipoSolicitudJpaEntity toEntity(TipoSolicitud domain) {
        return new TipoSolicitudJpaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getDescripcion()
        );
    }
}
