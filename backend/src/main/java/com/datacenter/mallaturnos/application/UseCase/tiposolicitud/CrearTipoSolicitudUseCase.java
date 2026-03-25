package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud.CrearTipoSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;

import org.springframework.stereotype.Service;

@Service
public class CrearTipoSolicitudUseCase implements CrearTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;
    private final TipoSolicitudMapper tipoSolicitudMapper;

    public CrearTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository,
                                     TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    @Override
    public TipoSolicitudDto crearTipoSolicitud(TipoSolicitudDto tipoSolicitudDto) {

        if (tipoSolicitudRepository.findByNombre(tipoSolicitudDto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("El tipo de solicitud ya existe");
        }

        TipoSolicitud nuevoTipo = tipoSolicitudMapper.toDomain(tipoSolicitudDto);

        TipoSolicitud guardado = tipoSolicitudRepository.save(nuevoTipo);

        return tipoSolicitudMapper.toDto(guardado);
    }
}
