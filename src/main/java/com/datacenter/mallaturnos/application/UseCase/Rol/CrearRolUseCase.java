package com.datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.port.out.RolRepositoryPort;

import org.springframework.stereotype.Service;

@Service
public class CrearRolUseCase {

    private final RolRepositoryPort rolRepository;

    public CrearRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol ejecutar(String nombre, String descripcion) {
        
        if (rolRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("Este rol ya existe");
        }

        Rol nuevoRol = new Rol();
        nuevoRol.setNombre(nombre);
        nuevoRol.setDescripcion(descripcion);

        return rolRepository.save(nuevoRol);
    }
}