package com.datacenter.krono_12.infrastructure.adapter.out.persistence.adapter;

import org.springframework.stereotype.Component;

import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity.TurnoJpaEntity;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.TurnoJpaRepository;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto TurnoRepositoryPort
 */
@Component
public class TurnoPersistenceAdapter implements TurnoRepositoryPort {

    private final TurnoJpaRepository jpaRepository;

    public TurnoPersistenceAdapter(TurnoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    @Override
    public Optional<Turno> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    public List<Turno> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Turno> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).map(this::toDomain);
    }

    @Override
    public Turno save(Turno turno) {
        TurnoJpaEntity entity = toEntity(turno);
        TurnoJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private Turno toDomain(TurnoJpaEntity entity) {
        return new Turno(
                entity.getId(),
                entity.getNombre(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getHoraalmuerzo(),
                entity.getHorabreak()
        );
    }

    private TurnoJpaEntity toEntity(Turno domain) {
        return new TurnoJpaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getHoraInicio(),
                domain.getHoraFin(),
                domain.getHoraalmuerzo(),
                domain.getHorabreak()
        );
    }

}

