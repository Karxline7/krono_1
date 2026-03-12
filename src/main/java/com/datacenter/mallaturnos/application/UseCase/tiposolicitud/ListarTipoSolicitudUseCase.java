package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.port.in.tiposolicitud.ListarTipoSolicitudUseCasePort;

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