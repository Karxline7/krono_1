package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.in.tiposolicitud.EditarTipoSolicitudUseCasePort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditarTipoSolicitudUseCase implements EditarTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;
    private final TipoSolicitudMapper tipoSolicitudMapper;

    public EditarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository, TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    @Override
    public TipoSolicitudDto editarTipoSolicitud(Long id, TipoSolicitudDto tipoSolicitudDto) {
        Optional<TipoSolicitud> tipoExistente = tipoSolicitudRepository.findById(id);
        
        if (!tipoExistente.isPresent()) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }

        TipoSolicitud tipo = tipoExistente.get();
        tipo.setNombre(tipoSolicitudDto.getNombre());
        tipo.setDescripcion(tipoSolicitudDto.getDescripcion());

        return tipoSolicitudMapper.toDto(tipoSolicitudRepository.save(tipo));
    }
}
