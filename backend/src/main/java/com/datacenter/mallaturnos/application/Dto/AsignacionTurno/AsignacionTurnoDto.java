package com.datacenter.mallaturnos.application.Dto.AsignacionTurno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTurnoDto {
    private Long id;
    private Long funcionarioId;
    private Long turnoId;
    private LocalDate fecha;
}
