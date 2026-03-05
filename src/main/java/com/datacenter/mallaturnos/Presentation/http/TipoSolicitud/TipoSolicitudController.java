package com.datacenter.mallaturnos.Presentation.http.TipoSolicitud;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.application.UseCase.tiposolicitud.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-solicitud")
public class TipoSolicitudController {

    private final CrearTipoSolicitudUseCase crearTipoSolicitudUseCase;
    private final ObtenerTipoSolicitudUseCase obtenerTipoSolicitudUseCase;
    private final ListarTipoSolicitudUseCase listarTipoSolicitudUseCase;
    private final EditarTipoSolicitudUseCase editarTipoSolicitudUseCase;
    private final EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase;

    public TipoSolicitudController(CrearTipoSolicitudUseCase crearTipoSolicitudUseCase,
                                   ObtenerTipoSolicitudUseCase obtenerTipoSolicitudUseCase,
                                   ListarTipoSolicitudUseCase listarTiposSolicitudUseCase,
                                   EditarTipoSolicitudUseCase editarTipoSolicitudUseCase,
                                   EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase) {
        this.crearTipoSolicitudUseCase = crearTipoSolicitudUseCase;
        this.obtenerTipoSolicitudUseCase = obtenerTipoSolicitudUseCase;
        this.listarTipoSolicitudUseCase = listarTiposSolicitudUseCase;
        this.editarTipoSolicitudUseCase = editarTipoSolicitudUseCase;
        this.eliminarTipoSolicitudUseCase = eliminarTipoSolicitudUseCase;
    }

    @PostMapping
    public ResponseEntity<TipoSolicitud> crear(@RequestBody CrearTipoSolicitudRequest request) {
        TipoSolicitud tipo = crearTipoSolicitudUseCase.ejecutar(request.getNombre(), request.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSolicitud> obtener(@PathVariable Long id) {
        Optional<TipoSolicitud> tipo = obtenerTipoSolicitudUseCase.ejecutar(id);
        return tipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TipoSolicitud>> listar() {
        List<TipoSolicitud> tipos = listarTipoSolicitudUseCase.ejecutar();
        return ResponseEntity.ok(tipos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSolicitud> editar(@PathVariable Long id,
                                                @RequestBody EditarTipoSolicitudRequest request) {
        TipoSolicitud tipo = editarTipoSolicitudUseCase.ejecutar(id, request.getNombre(), request.getDescripcion(), request.getActivo());
        return ResponseEntity.ok(tipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTipoSolicitudUseCase.ejecutar(id);
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
    private Boolean activo;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
