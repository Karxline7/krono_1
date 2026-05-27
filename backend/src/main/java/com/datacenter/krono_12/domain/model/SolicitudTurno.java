package com.datacenter.krono_12.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudTurno {
    private Long id;
    private Long asignacionTurnoId;
    private Long tipoSolicitudId;
    private String motivoSolicitud;
    private EstadoSolicitud estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAsignacionTurnoId() { return asignacionTurnoId; }
    public void setAsignacionTurnoId(Long asignacionTurnoId) { this.asignacionTurnoId = asignacionTurnoId; }
    public Long getTipoSolicitudId() { return tipoSolicitudId; }
    public void setTipoSolicitudId(Long tipoSolicitudId) { this.tipoSolicitudId = tipoSolicitudId; }
    public String getMotivoSolicitud() { return motivoSolicitud; }
    public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
}
