package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.port.in.tiposolicitud.ObtenerTipoSolicitudUseCasePort;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerTipoSolicitudUseCase implements ObtenerTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public ObtenerTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public Optional<TipoSolicitud> obtenerTipoSolicitud(Long id) {
        return tipoSolicitudRepository.findById(id);
    }
}
