package com.datacenter.krono_12.infrastructure.controller.Turno;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.application.Dto.Turno.Request.CrearTurnoRequest;
import com.datacenter.krono_12.application.Dto.Turno.Request.EditarTurnoRequest;
import com.datacenter.krono_12.application.UseCase.turno.*;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final CrearTurnoUseCase crearTurnoUseCase;
    private final ObtenerTurnoUseCase obtenerTurnoUseCase;
    private final ListarTurnosUseCase listarTurnosUseCase;
    private final EditarTurnoUseCase editarTurnoUseCase;
    private final EliminarTurnoUseCase eliminarTurnoUseCase;

    public TurnoController(
            CrearTurnoUseCase crearTurnoUseCase,
            ObtenerTurnoUseCase obtenerTurnoUseCase,
            ListarTurnosUseCase listarTurnosUseCase,
            EditarTurnoUseCase editarTurnoUseCase,
            EliminarTurnoUseCase eliminarTurnoUseCase,
            TurnoMapper turnoMapper) {

        this.crearTurnoUseCase = crearTurnoUseCase;
        this.obtenerTurnoUseCase = obtenerTurnoUseCase;
        this.listarTurnosUseCase = listarTurnosUseCase;
        this.editarTurnoUseCase = editarTurnoUseCase;
        this.eliminarTurnoUseCase = eliminarTurnoUseCase;
    }

    @PostMapping
    public ResponseEntity<TurnoDto> crear(@RequestBody CrearTurnoRequest request) {

        TurnoDto dto = new TurnoDto(
                null,
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraAlmuerzo(),
                request.getHoraBreak()
        );

        TurnoDto turnoCreado = crearTurnoUseCase.crearTurno(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(turnoCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> obtener(@PathVariable Long id) {

        Optional<TurnoDto> turno = obtenerTurnoUseCase.obtenerTurno(id);

        return turno
                .map(t -> ResponseEntity.ok(t))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TurnoDto>> listar() {

        List<TurnoDto> turnos = listarTurnosUseCase.listarTurnos();

        return ResponseEntity.ok(turnos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDto> editar(
            @PathVariable Long id,
            @RequestBody EditarTurnoRequest request) {

        TurnoDto dto = new TurnoDto(
                id,
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraalmuerzo(),
                request.getHorabreak()
        );

        TurnoDto turnoActualizado = editarTurnoUseCase.editarTurno(id, dto);

        return ResponseEntity.ok(turnoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        eliminarTurnoUseCase.eliminarTurno(id);

        return ResponseEntity.noContent().build();
    }
}