package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.port.in.tiposolicitud.CrearTipoSolicitudUseCasePort;

import org.springframework.stereotype.Service;

@Service
public class CrearTipoSolicitudUseCase implements CrearTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public CrearTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public TipoSolicitud crearTipoSolicitud(String nombre, String descripcion) {
        
        if (tipoSolicitudRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("El tipo de solicitud ya existe");
        }

        TipoSolicitud nuevoTipo = new TipoSolicitud();
        nuevoTipo.setNombre(nombre);
        nuevoTipo.setDescripcion(descripcion);
        
        return tipoSolicitudRepository.save(nuevoTipo);
    }
}
