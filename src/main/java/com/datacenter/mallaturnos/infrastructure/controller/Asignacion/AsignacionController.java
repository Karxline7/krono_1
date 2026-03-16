package com.datacenter.mallaturnos.infrastructure.controller.Asignacion;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.application.UseCase.asignacion.*;
import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.Request.AsignarTurnoRequest;
import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.Request.EditarTurnoAsignadoRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignarTurnoUseCase asignarTurnoUseCase;
    private final ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioPorTurnoAsignadoUseCase;
    private final EditarTurnoAsignadoUseCase editarTurnoAsignadoUseCase;
    private final EliminarTurnoAsignadoUseCase eliminarTurnoAsignadoUseCase;
    private final ListarAsignacionesPorFechaUseCase listarAsignacionesPorFecha;

    public AsignacionController(AsignarTurnoUseCase asignarTurnoUseCase,
                                ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioPorTurnoAsignadoUseCase,
                                EditarTurnoAsignadoUseCase editarTurnoAsignadoUseCase,
                                EliminarTurnoAsignadoUseCase eliminarTurnoAsignadoUseCase,
                                ListarAsignacionesPorFechaUseCase listarAsignacionesPorFecha) {
        this.asignarTurnoUseCase = asignarTurnoUseCase;
        this.obtenerUsuarioPorTurnoAsignadoUseCase = obtenerUsuarioPorTurnoAsignadoUseCase;
        this.editarTurnoAsignadoUseCase = editarTurnoAsignadoUseCase;
        this.eliminarTurnoAsignadoUseCase = eliminarTurnoAsignadoUseCase;
        this.listarAsignacionesPorFecha = listarAsignacionesPorFecha;
    }

    @PostMapping
    public ResponseEntity<AsignacionTurnoDto> asignar(@RequestBody AsignarTurnoRequest request) {

        AsignacionTurnoDto dto = new AsignacionTurnoDto(
                null,
                request.getFuncionarioId(),
                request.getTurnoId(),
                request.getFecha()
        );

        AsignacionTurnoDto asignacion = asignarTurnoUseCase.asignarTurno(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(asignacion);
    }

    @GetMapping("/funcionario/{funcionarioId}/fecha/{fecha}")
    public ResponseEntity<UsuarioDto> obtenerUsuario(@PathVariable Long funcionarioId,
                                                     @PathVariable String fecha) {

        LocalDate fechaDate = LocalDate.parse(fecha);

        Optional<UsuarioDto> usuario =
                obtenerUsuarioPorTurnoAsignadoUseCase.obtenerUsuarioPorTurnoAsignado(funcionarioId, fechaDate);

        return usuario
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionTurnoDto> editar(@PathVariable Long id,
                                                     @RequestBody EditarTurnoAsignadoRequest request) {

        AsignacionTurnoDto dto = new AsignacionTurnoDto(
                id,
                request.getNuevoFuncionarioId(),
                request.getNuevoTurnoId(),
                request.getNuevaFecha()
        );

        AsignacionTurnoDto asignacionActualizada =
                editarTurnoAsignadoUseCase.editarTurnoAsignado(id, dto);

        return ResponseEntity.ok(asignacionActualizada);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AsignacionTurnoDto>> listarPorFecha(@PathVariable String fecha) {

        LocalDate fechaConsulta = LocalDate.parse(fecha);

        List<AsignacionTurnoDto> asignaciones =
                listarAsignacionesPorFecha.listarAsignacionesPorFecha(fechaConsulta);

        return ResponseEntity.ok(asignaciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        eliminarTurnoAsignadoUseCase.eliminarTurnoAsignado(id);

        return ResponseEntity.noContent().build();
    }
}