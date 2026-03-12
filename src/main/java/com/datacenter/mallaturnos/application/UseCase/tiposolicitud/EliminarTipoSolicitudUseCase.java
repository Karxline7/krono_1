package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.port.in.tiposolicitud.EliminarTipoSolicitudUseCasePort;

@Service
public class EliminarTipoSolicitudUseCase implements EliminarTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public EliminarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public boolean eliminarTipoSolicitud(Long id) {

        if (!tipoSolicitudRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }

        tipoSolicitudRepository.delete(id);
        return true;
    }
}