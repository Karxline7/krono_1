package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.port.out.TipoSolicitudRepositoryPort;
import com.datacenter.mallaturnos.port.in.tiposolicitud.EditarTipoSolicitudUseCasePort;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditarTipoSolicitudUseCase implements EditarTipoSolicitudUseCasePort {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public EditarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public TipoSolicitud editarTipoSolicitud(Long id, String nombre, String descripcion) {
        
        Optional<TipoSolicitud> tipoExistente = tipoSolicitudRepository.findById(id);
        
        if (!tipoExistente.isPresent()) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }

        TipoSolicitud tipo = tipoExistente.get();
        tipo.setNombre(nombre);
        tipo.setDescripcion(descripcion);

        return tipoSolicitudRepository.save(tipo);
    }
}
