package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;

@Service
public class EliminarTipoSolicitudUseCase {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public EliminarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public boolean ejecutar(Long id) {
        
        if (!tipoSolicitudRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }

        tipoSolicitudRepository.delete(id);
        return true;
    }
}