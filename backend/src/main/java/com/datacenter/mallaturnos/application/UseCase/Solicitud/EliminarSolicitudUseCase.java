package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import org.springframework.stereotype.Service;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;

@Service
public class EliminarSolicitudUseCase {
    

    private final SolicitudRepositoryPort solicitudRepositoryPort;

    public EliminarSolicitudUseCase(SolicitudRepositoryPort solicitudRepositoryPort) {
        this.solicitudRepositoryPort = solicitudRepositoryPort;
    }

    public void eliminarSolicitud(Long id) {

        if (!solicitudRepositoryPort.existsById(id)) {
            throw new RuntimeException("Solicitud no encontrada con id: " + id);
        }

        solicitudRepositoryPort.deleteById(id);
    }
}
