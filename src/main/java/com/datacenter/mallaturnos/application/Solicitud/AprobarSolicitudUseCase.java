package com.datacenter.mallaturnos.application.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.port.out.SolicitudRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AprobarSolicitudUseCase {

    private final SolicitudRepositoryPort solicitudRepository;


    public AprobarSolicitudUseCase(SolicitudRepositoryPort solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public SolicitudTurno ejecutar(Long solicitudId) {
        
        var solicitud = solicitudRepository.findById(solicitudId);
        
        if (!solicitud.isPresent()) {
            throw new IllegalArgumentException("Solicitud no encontrada");
        }

        SolicitudTurno sol = solicitud.get();
        
        if (!sol.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new IllegalArgumentException("La solicitud ya se encuentra resuelta");
        }

        sol.setEstado(EstadoSolicitud.APROBADA);

        return solicitudRepository.save(sol);
    }
}
