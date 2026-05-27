package com.datacenter.krono_12.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.in.tiposolicitud.ListarTipoSolicitudUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

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