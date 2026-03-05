package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.usuario;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto UsuarioRepositoryPort
 * Convierte entre Modelos de Dominio y Entidades JPA
 */
@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> findByNumeroDocumento(Integer numeroDocumento) {
        return jpaRepository.findByNumeroDocumento(numeroDocumento).map(this::toDomain);
    }

    @Override
    public boolean existsByNumeroDocumento(Integer numeroDocumento) {
        return jpaRepository.existsByNumeroDocumento(numeroDocumento);
    }

    @Override
    public List<Usuario> findByAreaIdAndRol(Long areaId, Rol rol) {
        return jpaRepository.findByAreaIdAndRol(areaId, rol)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findByAreaId(Long areaId) {
        return jpaRepository.findByAreaId(areaId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<Usuario> findByCargoId(Long cargoId) {
        return jpaRepository.findByCargoId(cargoId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioJpaEntity entity = toEntity(usuario);
        UsuarioJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    /**
     * Convierte de Entity JPA a Modelo de Dominio
     */
    private Usuario toDomain(UsuarioJpaEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNombres(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getContrasena(),
                entity.getRol(),
                entity.getCargoId(),
                entity.getAreaId(),
                entity.getActivo()
        );
    }

    /**
     * Convierte de Modelo de Dominio a Entity JPA
     */
    private UsuarioJpaEntity toEntity(Usuario domain) {
        return new UsuarioJpaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getTipoDocumento(),
                domain.getNumeroDocumento(),
                domain.getContrasena(),
                domain.getRol(),
                domain.getCargoId(),
                domain.getAreaId(),
                domain.getActivo()
        );
    }
}
