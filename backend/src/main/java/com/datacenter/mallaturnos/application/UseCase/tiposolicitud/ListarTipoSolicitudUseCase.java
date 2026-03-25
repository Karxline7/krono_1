package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud.ListarTipoSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTipoSolicitudUseCase implements ListarTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;
    private final TipoSolicitudMapper tipoSolicitudMapper;

    public ListarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository, TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    @Override
    public List<TipoSolicitudDto> listarTiposSolicitud() {
        return tipoSolicitudRepository.findAll().stream()
                .map(tipoSolicitudMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
}