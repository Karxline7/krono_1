package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.domain.model.EstadoSolicitud;
import jakarta.persistence.*;

/**
 * Entidad JPA: Mapea a la tabla solicitudes_turno
 */
@Entity
@Table(name = "solicitudes_turno")
public class SolicitudTurnoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSolicitud tipo;

    @Column(name = "motivo_solicitud")
    private String motivoSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "asignacion_turno_id", nullable = false)
    private AsignacionTurnoJpaEntity asignacionTurno;

    // Constructor vacío
    public SolicitudTurnoJpaEntity() {}

    // Constructor con todos los parámetros
    public SolicitudTurnoJpaEntity(
            Long id,
            TipoSolicitud tipo,
            String motivoSolicitud,
            EstadoSolicitud estado,
            AsignacionTurnoJpaEntity asignacionTurno) {
        this.id = id;
        this.tipo = tipo;
        this.motivoSolicitud = motivoSolicitud;
        this.estado = estado;
        this.asignacionTurno = asignacionTurno;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoSolicitud getTipo() { return tipo; }
    public void setTipo(TipoSolicitud tipo) { this.tipo = tipo; }

    public String getMotivoSolicitud() { return motivoSolicitud; }
    public void setMotivoSolicitud(String motivoSolicitud) { this.motivoSolicitud = motivoSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    public AsignacionTurnoJpaEntity getAsignacionTurno() { return asignacionTurno; }
    public void setAsignacionTurno(AsignacionTurnoJpaEntity asignacionTurno) { 
        this.asignacionTurno = asignacionTurno; 
    }
}
