package com.datacenter.krono_12.application.UseCase.tiposolicitud;

import org.springframework.stereotype.Service;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.in.tiposolicitud.CrearTipoSolicitudUseCasePort;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

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
