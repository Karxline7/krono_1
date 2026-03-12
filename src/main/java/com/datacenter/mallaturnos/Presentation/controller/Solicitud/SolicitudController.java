package com.datacenter.mallaturnos.Presentation.controller.Solicitud;

import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.application.UseCase.Solicitud.*;
import com.datacenter.mallaturnos.Presentation.Dto.SolicitudTurnoDto;
import com.datacenter.mallaturnos.Presentation.mappers.SolicitudTurnoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudTurnoUseCase solicitudTurnoUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final DenegarSolicitudUseCase denegarSolicitudUseCase;
    private final SolicitudTurnoMapper solicitudTurnoMapper;

    public SolicitudController(SolicitudTurnoUseCase solicitudTurnoUseCase,
                               AprobarSolicitudUseCase aprobarSolicitudUseCase,
                               DenegarSolicitudUseCase denegarSolicitudUseCase,
                               SolicitudTurnoMapper solicitudTurnoMapper) {
        this.solicitudTurnoUseCase = solicitudTurnoUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.denegarSolicitudUseCase = denegarSolicitudUseCase;
        this.solicitudTurnoMapper = solicitudTurnoMapper;
    }

    @PostMapping("/solicitud")
    public ResponseEntity<SolicitudTurnoDto> solicitarTurno(@RequestBody SolicitudTurnoRequest request) {
        SolicitudTurno solicitud = solicitudTurnoUseCase.crearSolicitudTurno(
                request.getAsignacionId(),
                request.getTipoSolicitudId(),
                request.getMotivoSolicitud(),
                request.getEstado()
        );
        SolicitudTurnoDto dto = solicitudTurnoMapper.toDto(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudTurnoDto> aprobar(@PathVariable Long id,
                                                     @RequestBody AprobarSolicitudRequest request) {
        SolicitudTurno solicitud = aprobarSolicitudUseCase.aprobarSolicitud(id);
        SolicitudTurnoDto dto = solicitudTurnoMapper.toDto(solicitud);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/denegar")
    public ResponseEntity<SolicitudTurnoDto> denegar(@PathVariable Long id,
                                                     @RequestBody DenegarSolicitudRequest request) {
        SolicitudTurno solicitud = denegarSolicitudUseCase.denegarSolicitud(id);
        SolicitudTurnoDto dto = solicitudTurnoMapper.toDto(solicitud);
        return ResponseEntity.ok(dto);
    }
}

class SolicitudTurnoRequest {
    private Long asignacionId;
    private Long tipoSolicitudId;
    private String motivoSolicitud;
    private EstadoSolicitud estado;

    public Long getAsignacionId() { return asignacionId; }
    public void setAsignacionId(Long asignacionId) { this.asignacionId = asignacionId; }

    public Long getTipoSolicitudId() { return tipoSolicitudId; }
    public void setTipoSolicitudId(Long tipoSolicitudId) { this.tipoSolicitudId = tipoSolicitudId; }

    public String getMotivoSolicitud() { return motivoSolicitud; }
    public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
}

class AprobarSolicitudRequest {
    private EstadoSolicitud Estado;

    public EstadoSolicitud getEstado() { return Estado; }
    public void setEstado(EstadoSolicitud Estado) { this.Estado = Estado; }
}

class DenegarSolicitudRequest {
    private EstadoSolicitud Estado;

    public EstadoSolicitud getEstado() { return Estado; }
    public void setEstado(EstadoSolicitud Estado) { this.Estado = Estado; }
}