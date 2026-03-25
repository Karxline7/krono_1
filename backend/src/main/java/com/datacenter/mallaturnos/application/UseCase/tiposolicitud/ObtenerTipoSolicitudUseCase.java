package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud.ObtenerTipoSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;

import org.springframework.stereotype.Service;

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
