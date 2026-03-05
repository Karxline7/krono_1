package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerTipoSolicitudUseCase {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public ObtenerTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public Optional<TipoSolicitud> ejecutar(Long id) {
        return tipoSolicitudRepository.findById(id);
    }
}
