package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.adapter;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.AsignacionTurnoJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.AsignacionJpaRepository;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto AsignacionRepositoryPort
 */
@Component
public class AsignacionPersistenceAdapter implements AsignacionRepositoryPort {

    private final AsignacionJpaRepository jpaRepository;

    public AsignacionPersistenceAdapter(AsignacionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<AsignacionTurno> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

  public Optional<AsignacionTurno> findByFuncionarioAndFecha(Long funcionarioId, LocalDate fecha) {
    return jpaRepository
            .findByFuncionario_IdAndFecha(funcionarioId, fecha)
            .map(this::toDomain);
}

    public boolean existsByFuncionarioAndFecha(Long funcionarioId, LocalDate fecha) {
        return jpaRepository.existsByFuncionario_IdAndFecha(funcionarioId, fecha);
    }

    public List<AsignacionTurno> findByAreaAndPeriodo(Long areaId, LocalDate inicio, LocalDate fin) {
        return jpaRepository.findByAreaAndPeriodo(areaId, inicio, fin)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<AsignacionTurno> findByFuncionarioAndPeriodo(
            Long funcionarioId,
            LocalDate inicio,
            LocalDate fin
    ) {
        return jpaRepository
                .findByFuncionario_IdAndFechaBetween(funcionarioId, inicio, fin)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<AsignacionTurno> findByFecha(LocalDate fecha) {

        return jpaRepository.findByFecha(fecha)
                .stream()
                .map(entity -> {
                    AsignacionTurno asignacion = new AsignacionTurno();
                    asignacion.setId(entity.getId());
                    asignacion.setFuncionarioId(entity.getFuncionario().getId());
                    asignacion.setTurnoId(entity.getTurnoId());
                    asignacion.setFecha(entity.getFecha());
                    return asignacion;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AsignacionTurno save(AsignacionTurno asignacion) {
        AsignacionTurnoJpaEntity entity = toEntity(asignacion);
        AsignacionTurnoJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<AsignacionTurnoDto> listarAsignaciones() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AsignacionTurnoDto> listarAsignacionesPorUsuario(Long usuarioId) {
        return jpaRepository.findByFuncionario_Id(usuarioId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AsignacionTurnoDto> listarAsignacionesPorArea(Long areaId) {
        return jpaRepository.findByFuncionario_AreaId(areaId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

private AsignacionTurno toDomain(AsignacionTurnoJpaEntity entity) {
    return new AsignacionTurno(
            entity.getId(),
            entity.getFuncionario().getId(),
            entity.getTurnoId(),
            entity.getFecha()
    );
}

private AsignacionTurnoDto toDto(AsignacionTurnoJpaEntity entity) {
    AsignacionTurnoDto dto = new AsignacionTurnoDto();
    dto.setId(entity.getId());
    dto.setFuncionarioId(entity.getFuncionario().getId());
    dto.setTurnoId(entity.getTurnoId());
    dto.setFecha(entity.getFecha());

    return dto;
}

private AsignacionTurnoJpaEntity toEntity(AsignacionTurno domain) {

    UsuarioJpaEntity funcionario = new UsuarioJpaEntity();
    funcionario.setId(domain.getFuncionarioId());

    AsignacionTurnoJpaEntity entity = new AsignacionTurnoJpaEntity();
    entity.setId(domain.getId());
    entity.setFuncionario(funcionario);
    entity.setTurnoId(domain.getTurnoId());
    entity.setFecha(domain.getFecha());

    return entity;
}
}
