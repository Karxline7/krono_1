package com.datacenter.mallaturnos.Presentation.http.Solicitud;

import com.datacenter.mallaturnos.application.UseCase.Solicitud.*;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudTurnoUseCase solicitudTurnoUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final DenegarSolicitudUseCase denegarSolicitudUseCase;

    public SolicitudController(
            SolicitudTurnoUseCase solicitudTurnoUseCase,
            AprobarSolicitudUseCase aprobarSolicitudUseCase,
            DenegarSolicitudUseCase denegarSolicitudUseCase) {

        this.solicitudTurnoUseCase = solicitudTurnoUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.denegarSolicitudUseCase = denegarSolicitudUseCase;
    }

    // =========================
    // SOLICITAR CAMBIO
    // =========================
    @PostMapping("/cambio")
    public ResponseEntity<SolicitudTurno> solicitudTurno(
            @RequestBody SolicitudTurnoRequest request) {

        SolicitudTurno solicitud = solicitudTurnoUseCase.ejecutar(
                request.getAsignacionId(),
                request.getTipoSolicitudId(),
                request.getMotivoSolicitud(),
                request.getEstado()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
    }

    // =========================
    // APROBAR
    // =========================
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudTurno> aprobar(@PathVariable Long id) {
    SolicitudTurno solicitud = aprobarSolicitudUseCase.ejecutar(id);
    return ResponseEntity.ok(solicitud);
}

    // =========================
    // DENEGAR
    // =========================
    @PutMapping("/{id}/denegar")
    public ResponseEntity<SolicitudTurno> denegar(
            @PathVariable Long id,
            @RequestBody DenegarSolicitudRequest request) {
        SolicitudTurno solicitud = denegarSolicitudUseCase.ejecutar(id);
        return ResponseEntity.ok(solicitud);
    }

    // =====================================================
    // ========= CLASES INTERNAS =========
    // =====================================================

    public static class SolicitudTurnoRequest {
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

    public static class AprobarSolicitudRequest {
        private Long solicitudId;

        public Long getSolicitudId() { return solicitudId; }
        public void setSolicitudId(Long solicitudId) { this.solicitudId = solicitudId; }
    }

    public static class DenegarSolicitudRequest {
        private Long solicitudId;
        private String motivoRespuesta;

        public Long getSolicitudId() { return solicitudId; }
        public void setSolicitudId(Long solicitudId) { this.solicitudId = solicitudId; }

        public String getMotivoRespuesta() { return motivoRespuesta; }
        public void setMotivoRespuesta(String motivoRespuesta) { this.motivoRespuesta = motivoRespuesta; }
    }
}