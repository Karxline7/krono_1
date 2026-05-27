package com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity;

import com.datacenter.krono_12.domain.model.EstadoSolicitud;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Mapea a la tabla solicitudes_turno, se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "solicitudes_turno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudTurnoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_solicitud_id", nullable = false)
    private TipoSolicitudJpaEntity tipoSolicitud;

    @Column(name = "motivo_solicitud")
    private String motivoSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "asignacion_turno_id", nullable = false)
    private AsignacionTurnoJpaEntity asignacionTurno;
}