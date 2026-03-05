package com.datacenter.mallaturnos.Presentation.http.Solicitud;

import com.datacenter.mallaturnos.application.UseCase.Solicitud.*;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitarCambioTurnoUseCase solicitarCambioTurnoUseCase;
    private final SolicitarEliminacionTurnoUseCase solicitarEliminacionTurnoUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final DenegarSolicitudUseCase denegarSolicitudUseCase;

    public SolicitudController(
            SolicitarCambioTurnoUseCase solicitarCambioTurnoUseCase,
            SolicitarEliminacionTurnoUseCase solicitarEliminacionTurnoUseCase,
            AprobarSolicitudUseCase aprobarSolicitudUseCase,
            DenegarSolicitudUseCase denegarSolicitudUseCase) {

        this.solicitarCambioTurnoUseCase = solicitarCambioTurnoUseCase;
        this.solicitarEliminacionTurnoUseCase = solicitarEliminacionTurnoUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.denegarSolicitudUseCase = denegarSolicitudUseCase;
    }

    // =========================
    // SOLICITAR CAMBIO
    // =========================
    @PostMapping("/cambio")
    public ResponseEntity<SolicitudTurno> solicitarCambio(
            @RequestBody SolicitarCambioRequest request) {

        SolicitudTurno solicitud = solicitarCambioTurnoUseCase.ejecutar(
                request.getAsignacionId(),
                request.getFuncionarioId(),
                request.getMotivoSolicitud()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitud);
    }

    // =========================
    // SOLICITAR ELIMINACIÓN
    // =========================
    @PostMapping("/eliminacion")
    public ResponseEntity<SolicitudTurno> solicitarEliminacion(
            @RequestBody SolicitarEliminacionRequest request) {

        SolicitudTurno solicitud = solicitarEliminacionTurnoUseCase.ejecutar(
                request.getAsignacionId(),
                request.getFuncionarioId(),
                request.getMotivoSolicitud()
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
    // ========= CLASES INTERNAS (IMPORTANTÍSIMO) =========
    // =====================================================

    public static class SolicitarCambioRequest {
        private Long asignacionId;
        private Long funcionarioId;
        private String motivoSolicitud;

        public Long getAsignacionId() { return asignacionId; }
        public void setAsignacionId(Long asignacionId) { this.asignacionId = asignacionId; }

        public Long getFuncionarioId() { return funcionarioId; }
        public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

        public String getMotivoSolicitud() { return motivoSolicitud; }
        public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }
    }

    public static class SolicitarEliminacionRequest {
        private Long asignacionId;
        private Long funcionarioId;
        private String motivoSolicitud;

        public Long getAsignacionId() { return asignacionId; }
        public void setAsignacionId(Long asignacionId) { this.asignacionId = asignacionId; }

        public Long getFuncionarioId() { return funcionarioId; }
        public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

        public String getMotivoSolicitud() { return motivoSolicitud; }
        public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }
    }

    public static class AprobarSolicitudRequest {
        private Long supervisorId;

        public Long getSupervisorId() { return supervisorId; }
        public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }
    }

    public static class DenegarSolicitudRequest {
        private Long supervisorId;
        private String motivoRespuesta;

        public Long getSupervisorId() { return supervisorId; }
        public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }

        public String getMotivoRespuesta() { return motivoRespuesta; }
        public void setMotivoRespuesta(String motivoRespuesta) { this.motivoRespuesta = motivoRespuesta; }
    }
}