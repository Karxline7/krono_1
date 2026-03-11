package com.datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.port.out.SolicitudRepositoryPort;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;

import org.springframework.stereotype.Service;

@Service
public class DenegarSolicitudUseCase {

    private final SolicitudRepositoryPort solicitudRepository;

    public DenegarSolicitudUseCase(SolicitudRepositoryPort solicitudRepository) {
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

        sol.setEstado(EstadoSolicitud.DENEGADA);

        return solicitudRepository.save(sol);
    }
}
