package com.datacenter.krono_12.infrastructure.adapter.out.persistence.adapter;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.RolJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.RolJpaRepository;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto RolRepositoryPort
 */
@Component
public class RolPersistenceAdapter implements RolRepositoryPort {

    private final RolJpaRepository jpaRepository;

    public RolPersistenceAdapter(RolJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).map(this::toDomain);
    }

    @Override
    public List<Rol> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Rol save(Rol rol) {
        RolJpaEntity entity = toEntity(rol);
        RolJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private Rol toDomain(RolJpaEntity entity) {
        return new Rol(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion()
        );
    }

    private RolJpaEntity toEntity(Rol domain) {
        return new RolJpaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getDescripcion()
        );
    }
}
