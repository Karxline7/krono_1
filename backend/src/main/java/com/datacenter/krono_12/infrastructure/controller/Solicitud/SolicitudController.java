package com.datacenter.krono_12.infrastructure.controller.Solicitud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.application.Dto.SolicitudTurno.Request.AprobarSolicitudRequest;
import com.datacenter.krono_12.application.Dto.SolicitudTurno.Request.DenegarSolicitudRequest;
import com.datacenter.krono_12.application.UseCase.Solicitud.*;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudTurnoUseCase solicitudTurnoUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final DenegarSolicitudUseCase denegarSolicitudUseCase;
    private final ListarSolicitudesUseCase listarSolicitudesUseCase;
    private final EliminarSolicitudUseCase eliminarSolicitudUseCase;

    public SolicitudController(SolicitudTurnoUseCase solicitudTurnoUseCase,
                               AprobarSolicitudUseCase aprobarSolicitudUseCase,
                               DenegarSolicitudUseCase denegarSolicitudUseCase,
                               ListarSolicitudesUseCase listarSolicitudesUseCase,
                               SolicitudTurnoMapper solicitudTurnoMapper,
                               EliminarSolicitudUseCase eliminarSolicitudUseCase) {
        this.solicitudTurnoUseCase = solicitudTurnoUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.denegarSolicitudUseCase = denegarSolicitudUseCase;
        this.listarSolicitudesUseCase = listarSolicitudesUseCase;
        this.eliminarSolicitudUseCase = eliminarSolicitudUseCase;
    }

    @PostMapping("/solicitud")
    public ResponseEntity<SolicitudTurnoDto> solicitarTurno(@RequestBody SolicitudTurnoDto dto) {

        SolicitudTurnoDto solicitud = solicitudTurnoUseCase.crearSolicitudTurno(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitud);
    }
    @GetMapping
    public ResponseEntity<List<SolicitudTurnoDto>> listarSolicitudes() {
        return ResponseEntity.ok(listarSolicitudesUseCase.listarSolicitudes());
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudTurnoDto> aprobar(@PathVariable Long id,
                                                     @RequestBody AprobarSolicitudRequest request) {
        SolicitudTurnoDto solicitud = aprobarSolicitudUseCase.aprobarSolicitud(id);
        return ResponseEntity.ok(solicitud);
    }

    @PutMapping("/{id}/denegar")
    public ResponseEntity<SolicitudTurnoDto> denegar(@PathVariable Long id,
                                                     @RequestBody DenegarSolicitudRequest request) {
        SolicitudTurnoDto solicitud = denegarSolicitudUseCase.denegarSolicitud(id);
        return ResponseEntity.ok(solicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        eliminarSolicitudUseCase.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }

}