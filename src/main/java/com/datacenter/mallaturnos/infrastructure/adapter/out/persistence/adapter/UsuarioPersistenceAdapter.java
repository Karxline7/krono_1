package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.adapter;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.CargoJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.RolJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.repository.RolJpaRepository;

import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador de salida: Implementa el puerto UsuarioRepositoryPort
 * Convierte entre Modelos de Dominio y Entidades JPA
 */
@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final RolJpaRepository rolRepository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository jpaRepository, RolJpaRepository rolRepository) {
        this.jpaRepository = jpaRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> findByNumeroDocumento(Long numeroDocumento) {
        return jpaRepository.findByNumeroDocumento(numeroDocumento).map(this::toDomain);
    }

    @Override
    public boolean existsByNumeroDocumento(Long numeroDocumento) {
        return jpaRepository.existsByNumeroDocumento(numeroDocumento);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    @Override
    public List<UsuarioDto> findAllWithCargo() {

        return jpaRepository.findAll()
                .stream()
                .map(entity -> {

                    UsuarioDto dto = new UsuarioDto();

                    dto.setId(entity.getId());
                    dto.setNombre(entity.getNombre());
                    dto.setTipoDocumento(entity.getTipoDocumento());
                    dto.setNumeroDocumento(entity.getNumeroDocumento());

                    if (entity.getRol() != null) {
                        dto.setRolId(entity.getRol().getId());
                    }

                    if (entity.getCargo() != null) {
                        dto.setCargoId(entity.getCargo().getId());
                        dto.setCargoNombre(entity.getCargo().getNombre()); // 🔥 AQUÍ
                    }

                    dto.setAreaId(entity.getAreaId());

                    return dto;

                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findByAreaIdAndRolId(Long areaId, Long rolId) {
        return jpaRepository.findByAreaIdAndRolId(areaId, rolId)
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
                entity.getNombre(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getContrasena(),
                entity.getRol().getId(),
                entity.getCargo().getId(),
                entity.getAreaId()
        );
    }

    /**
     * Convierte de Modelo de Dominio a Entity JPA
     */
    private UsuarioJpaEntity toEntity(Usuario domain) {

        RolJpaEntity rol = rolRepository.findById(domain.getRolId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        CargoJpaEntity cargo = new CargoJpaEntity();
        cargo.setId(domain.getCargoId());

        return new UsuarioJpaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getTipoDocumento(),
                domain.getNumeroDocumento(),
                domain.getContrasena(),
                rol,
                cargo,
                domain.getAreaId()
        );
    }
}