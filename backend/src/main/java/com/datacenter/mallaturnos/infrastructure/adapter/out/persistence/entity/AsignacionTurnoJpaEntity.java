package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.time.LocalDate;

/**
 * Entidad JPA: Mapea a la tabla asignaciones_turno, se usa lombok para generar getters, setters y
 * constructores de forma automática.
 */
@Entity
@Table(name = "asignaciones_turno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTurnoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "turno_id", nullable = false)
    private Long turnoId;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private UsuarioJpaEntity funcionario;

    @OneToMany(mappedBy = "asignacionTurno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudTurnoJpaEntity> solicitudes;
}
