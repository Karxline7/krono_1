package com.datacenter.mallaturnos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA: Mapea a la tabla asignaciones_turno
 */
@Entity
@Table(name = "asignaciones_turno")
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

    public AsignacionTurnoJpaEntity() {
    }

    public AsignacionTurnoJpaEntity(Long id,
                                    UsuarioJpaEntity funcionario,
                                    Long turnoId,
                                    LocalDate fecha) {
        this.id = id;
        this.funcionario = funcionario;
        this.turnoId = turnoId;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsuarioJpaEntity getFuncionario() { return funcionario; }
    public void setFuncionario(UsuarioJpaEntity funcionario) { this.funcionario = funcionario; }

    public Long getTurnoId() { return turnoId; }
    public void setTurnoId(Long turnoId) { this.turnoId = turnoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
