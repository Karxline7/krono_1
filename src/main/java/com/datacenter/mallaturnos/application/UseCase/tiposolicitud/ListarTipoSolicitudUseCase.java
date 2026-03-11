package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTipoSolicitudUseCase {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public ListarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public List<TipoSolicitud> ejecutar() {
        return tipoSolicitudRepository.findAll();
    }
}