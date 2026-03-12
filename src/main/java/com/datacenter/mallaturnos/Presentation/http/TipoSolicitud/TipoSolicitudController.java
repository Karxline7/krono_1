package com.datacenter.mallaturnos.Presentation.http.TipoSolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.application.UseCase.tiposolicitud.*;
import com.datacenter.mallaturnos.Presentation.Dto.TipoSolicitudDto;
import com.datacenter.mallaturnos.Presentation.mappers.TipoSolicitudMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-solicitud")
public class TipoSolicitudController {

    private final CrearTipoSolicitudUseCase crearTipoSolicitudUseCase;
    private final ObtenerTipoSolicitudUseCase obtenerTipoSolicitudUseCase;
    private final ListarTipoSolicitudUseCase listarTiposSolicitudUseCase;
    private final EditarTipoSolicitudUseCase editarTipoSolicitudUseCase;
    private final EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase;
    private final TipoSolicitudMapper tipoSolicitudMapper;

    public TipoSolicitudController(CrearTipoSolicitudUseCase crearTipoSolicitudUseCase,
                                   ObtenerTipoSolicitudUseCase obtenerTipoSolicitudUseCase,
                                   ListarTipoSolicitudUseCase listarTiposSolicitudUseCase,
                                   EditarTipoSolicitudUseCase editarTipoSolicitudUseCase,
                                   EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase,
                                   TipoSolicitudMapper tipoSolicitudMapper) {
        this.crearTipoSolicitudUseCase = crearTipoSolicitudUseCase;
        this.obtenerTipoSolicitudUseCase = obtenerTipoSolicitudUseCase;
        this.listarTiposSolicitudUseCase = listarTiposSolicitudUseCase;
        this.editarTipoSolicitudUseCase = editarTipoSolicitudUseCase;
        this.eliminarTipoSolicitudUseCase = eliminarTipoSolicitudUseCase;
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    @PostMapping
    public ResponseEntity<TipoSolicitudDto> crear(@RequestBody CrearTipoSolicitudRequest request) {
        TipoSolicitud tipoSolicitud = crearTipoSolicitudUseCase.crearTipoSolicitud(request.getNombre(), request.getDescripcion());
        TipoSolicitudDto dto = tipoSolicitudMapper.toDto(tipoSolicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSolicitudDto> obtener(@PathVariable Long id) {
        Optional<TipoSolicitud> tipoSolicitud = obtenerTipoSolicitudUseCase.obtenerTipoSolicitud(id);
        return tipoSolicitud.map(t -> ResponseEntity.ok(tipoSolicitudMapper.toDto(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TipoSolicitudDto>> listar() {
        List<TipoSolicitud> tiposSolicitud = listarTiposSolicitudUseCase.listarTiposSolicitud();
        List<TipoSolicitudDto> dtos = tiposSolicitud.stream()
                .map(tipoSolicitudMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSolicitudDto> editar(@PathVariable Long id,
                                                @RequestBody EditarTipoSolicitudRequest request) {
        TipoSolicitud tipoSolicitud = editarTipoSolicitudUseCase.editarTipoSolicitud(id, request.getNombre(), request.getDescripcion());
        TipoSolicitudDto dto = tipoSolicitudMapper.toDto(tipoSolicitud);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTipoSolicitudUseCase.eliminarTipoSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}

class CrearTipoSolicitudRequest {
    private String nombre;
    private String descripcion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

class EditarTipoSolicitudRequest {
    private String nombre;
    private String descripcion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}
