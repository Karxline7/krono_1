package com.datacenter.mallaturnos.infrastructure.controller.Solicitud;

import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.application.UseCase.Solicitud.*;
import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request.AprobarSolicitudRequest;
import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request.DenegarSolicitudRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudTurnoUseCase solicitudTurnoUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final DenegarSolicitudUseCase denegarSolicitudUseCase;

    public SolicitudController(SolicitudTurnoUseCase solicitudTurnoUseCase,
                               AprobarSolicitudUseCase aprobarSolicitudUseCase,
                               DenegarSolicitudUseCase denegarSolicitudUseCase,
                               SolicitudTurnoMapper solicitudTurnoMapper) {
        this.solicitudTurnoUseCase = solicitudTurnoUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.denegarSolicitudUseCase = denegarSolicitudUseCase;
    }

    @PostMapping("/solicitud")
    public ResponseEntity<SolicitudTurnoDto> solicitarTurno(@RequestBody SolicitudTurnoDto dto) {

        SolicitudTurnoDto solicitud = solicitudTurnoUseCase.crearSolicitudTurno(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitud);
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
}