package com.datacenter.krono_12.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.infrastructure.port.in.tiposolicitud.EliminarTipoSolicitudUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

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