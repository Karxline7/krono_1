package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditarRolUseCase {

    private final RolRepositoryPort rolRepository;

    public EditarRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol ejecutar(Long id, String nombre, String descripcion) {
        
        Optional<Rol> rolExistente = rolRepository.findById(id);
        
        if (!rolExistente.isPresent()) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        Rol rol = rolExistente.get();
        rol.setNombre(nombre);
        rol.setDescripcion(descripcion);

        return rolRepository.save(rol);
    }
}