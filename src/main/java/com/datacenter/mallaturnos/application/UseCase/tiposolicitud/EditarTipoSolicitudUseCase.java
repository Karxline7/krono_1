package com.datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditarTipoSolicitudUseCase {

    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public EditarTipoSolicitudUseCase(TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public TipoSolicitud ejecutar(Long id, String nombre, String descripcion, Boolean activo) {
        
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
