package com.datacenter.mallaturnos.Presentation.http.Asignacion;

import com.datacenter.mallaturnos.application.UseCase.asignacion.*;
import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.domain.model.Usuario;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignarTurnoUseCase asignarTurnoUseCase;
    private final ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioPorTurnoAsignadoUseCase;
    private final EditarTurnoAsignadoUseCase editarTurnoAsignadoUseCase;
    private final EliminarTurnoAsignadoUseCase eliminarTurnoAsignadoUseCase;

    public AsignacionController(AsignarTurnoUseCase asignarTurnoUseCase,
                                ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioPorTurnoAsignadoUseCase,
                                EditarTurnoAsignadoUseCase editarTurnoAsignadoUseCase,
                                EliminarTurnoAsignadoUseCase eliminarTurnoAsignadoUseCase) {
        this.asignarTurnoUseCase = asignarTurnoUseCase;
        this.obtenerUsuarioPorTurnoAsignadoUseCase = obtenerUsuarioPorTurnoAsignadoUseCase;
        this.editarTurnoAsignadoUseCase = editarTurnoAsignadoUseCase;
        this.eliminarTurnoAsignadoUseCase = eliminarTurnoAsignadoUseCase;
    }

    // =========================
    // ASIGNAR TURNO
    // =========================
    @PostMapping
    public ResponseEntity<AsignacionTurno> asignar(@RequestBody AsignarTurnoRequest request) {
        AsignacionTurno asignacion = asignarTurnoUseCase.ejecutar(
                request.getFuncionarioId(),
                request.getTurnoId(),
                request.getFecha()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacion);
    }

    // =========================
    // OBTENER USUARIO POR TURNO ASIGNADO
    // =========================
    @GetMapping("/funcionario/{funcionarioId}/fecha/{fecha}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long funcionarioId,
                                                   @PathVariable LocalDate fecha) {
        Optional<Usuario> usuario = obtenerUsuarioPorTurnoAsignadoUseCase.ejecutar(funcionarioId, fecha);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // =========================
    // EDITAR TURNO ASIGNADO
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<AsignacionTurno> editar(@PathVariable Long id,
                                                   @RequestBody EditarTurnoAsignadoRequest request) {
        AsignacionTurno asignacion = editarTurnoAsignadoUseCase.ejecutar(
                id,
                request.getNuevoFuncionarioId(),
                request.getNuevaFecha(),
                request.getNuevoTurnoId()
        );
        return ResponseEntity.ok(asignacion);
    }

    // =========================
    // ELIMINAR TURNO ASIGNADO
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTurnoAsignadoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}

class AsignarTurnoRequest {
    private Long funcionarioId;
    private Long turnoId;
    private LocalDate fecha;

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public Long getTurnoId() { return turnoId; }
    public void setTurnoId(Long turnoId) { this.turnoId = turnoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}

class EditarTurnoAsignadoRequest {
    private Long nuevoFuncionarioId;
    private LocalDate nuevaFecha;
    private Long nuevoTurnoId;

    public Long getNuevoFuncionarioId() { return nuevoFuncionarioId; }
    public void setNuevoFuncionarioId(Long nuevoFuncionarioId) { this.nuevoFuncionarioId = nuevoFuncionarioId; }

    public LocalDate getNuevaFecha() { return nuevaFecha; }
    public void setNuevaFecha(LocalDate nuevaFecha) { this.nuevaFecha = nuevaFecha; }

    public Long getNuevoTurnoId() { return nuevoTurnoId; }
    public void setNuevoTurnoId(Long nuevoTurnoId) { this.nuevoTurnoId = nuevoTurnoId; }
}