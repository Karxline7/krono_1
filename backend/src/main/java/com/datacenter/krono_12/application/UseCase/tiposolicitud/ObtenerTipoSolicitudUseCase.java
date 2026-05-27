package com.datacenter.krono_12.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.in.tiposolicitud.ObtenerTipoSolicitudUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.Optional;

@Service
public class ObtenerTipoSolicitudUseCase implements ObtenerTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;
    private final TipoSolicitudMapper tipoSolicitudMapper;

    public ObtenerTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository, TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    @Override
    public Optional<TipoSolicitudDto> obtenerTipoSolicitud(Long id) {
        return tipoSolicitudRepository.findById(id).map(tipoSolicitudMapper::toDto);
    }
}
