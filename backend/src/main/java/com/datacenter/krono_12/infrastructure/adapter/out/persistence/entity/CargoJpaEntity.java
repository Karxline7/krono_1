package com.datacenter.krono_12.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cargo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoJpaEntity {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
}