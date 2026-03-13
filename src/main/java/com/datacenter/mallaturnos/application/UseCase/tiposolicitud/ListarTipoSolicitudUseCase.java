package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud.ListarTipoSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTipoSolicitudUseCase implements ListarTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public ListarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public List<TipoSolicitud> listarTiposSolicitud() {
        return tipoSolicitudRepository.findAll();
    }
}