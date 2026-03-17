package com.datacenter.mallaturnos.infrastructure.controller.TipoSolicitud;

import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.application.UseCase.tiposolicitud.*;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.Request.CrearTipoSolicitudRequest;
import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.Request.EditarTipoSolicitudRequest;

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
    private final ListarTipoSolicitudUseCase listarTiposSolicitudUseCase;
    private final EditarTipoSolicitudUseCase editarTipoSolicitudUseCase;
    private final EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase;

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
    }

    @PostMapping
    public ResponseEntity<TipoSolicitudDto> crear(@RequestBody CrearTipoSolicitudRequest request) {

        TipoSolicitudDto dto = new TipoSolicitudDto(
                null, // id
                request.getNombre(),
                request.getDescripcion()
        );

        TipoSolicitudDto response = crearTipoSolicitudUseCase.crearTipoSolicitud(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSolicitudDto> obtener(@PathVariable Long id) {

        Optional<TipoSolicitudDto> tipoSolicitud = obtenerTipoSolicitudUseCase.obtenerTipoSolicitud(id);

        return tipoSolicitud
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TipoSolicitudDto>> listar() {

        List<TipoSolicitudDto> tipos = listarTiposSolicitudUseCase.listarTiposSolicitud();

        return ResponseEntity.ok(tipos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSolicitudDto> editar(@PathVariable Long id,
                                                @RequestBody EditarTipoSolicitudRequest request) {

        TipoSolicitudDto dto = new TipoSolicitudDto(
                id,
                request.getNombre(),
                request.getDescripcion()
        );

        TipoSolicitudDto actualizado = editarTipoSolicitudUseCase.editarTipoSolicitud(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTipoSolicitudUseCase.eliminarTipoSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}

