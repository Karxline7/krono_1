package com.datacenter.mallaturnos.domain.model;

public class SolicitudTurno {
    private Long id;
    private Long asignacionTurnoId;
    private TipoSolicitud tipo;
    private String motivoSolicitud;
    private EstadoSolicitud estado;

    public SolicitudTurno() {
    }

    public SolicitudTurno(Long id, Long asignacionTurnoId,
                          TipoSolicitud tipo, String motivoSolicitud,
                          EstadoSolicitud estado) {
        this.id = id;
        this.asignacionTurnoId = asignacionTurnoId;
        this.tipo = tipo;
        this.motivoSolicitud = motivoSolicitud;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAsignacionTurnoId() { return asignacionTurnoId; }
    public void setAsignacionTurnoId(Long asignacionTurnoId) { this.asignacionTurnoId = asignacionTurnoId; }

    public TipoSolicitud getTipo() { return tipo; }
    public void setTipo(TipoSolicitud tipo) { this.tipo = tipo; }

    public String getMotivoSolicitud() { return motivoSolicitud; }
    public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
}
