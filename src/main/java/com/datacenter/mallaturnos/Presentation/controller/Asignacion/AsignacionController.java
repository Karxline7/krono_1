package com.datacenter.mallaturnos.Presentation.controller.Asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.application.UseCase.asignacion.*;
import com.datacenter.mallaturnos.Presentation.Dto.AsignacionTurnoDto;
import com.datacenter.mallaturnos.Presentation.Dto.UsuarioDto;
import com.datacenter.mallaturnos.Presentation.mappers.AsignacionTurnoMapper;
import com.datacenter.mallaturnos.Presentation.mappers.UsuarioMapper;
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
    private final AsignacionTurnoMapper asignacionTurnoMapper;
    private final UsuarioMapper usuarioMapper;

    public AsignacionController(AsignarTurnoUseCase asignarTurnoUseCase,
                                ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioPorTurnoAsignadoUseCase,
                                EditarTurnoAsignadoUseCase editarTurnoAsignadoUseCase,
                                EliminarTurnoAsignadoUseCase eliminarTurnoAsignadoUseCase,
                                ListarAsignacionesPorFechaUseCase listarAsignacionesPorFecha,
                                AsignacionTurnoMapper asignacionTurnoMapper,
                                UsuarioMapper usuarioMapper) {
        this.asignarTurnoUseCase = asignarTurnoUseCase;
        this.obtenerUsuarioPorTurnoAsignadoUseCase = obtenerUsuarioPorTurnoAsignadoUseCase;
        this.editarTurnoAsignadoUseCase = editarTurnoAsignadoUseCase;
        this.eliminarTurnoAsignadoUseCase = eliminarTurnoAsignadoUseCase;
        this.listarAsignacionesPorFecha = listarAsignacionesPorFecha;
        this.asignacionTurnoMapper = asignacionTurnoMapper;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity<AsignacionTurnoDto> asignar(@RequestBody AsignarTurnoRequest request) {
        AsignacionTurno asignacion = asignarTurnoUseCase.asignarTurno(
                request.getFuncionarioId(),
                request.getTurnoId(),
                request.getFecha()
        );
        AsignacionTurnoDto dto = asignacionTurnoMapper.toDto(asignacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/funcionario/{funcionarioId}/fecha/{fecha}")
    public ResponseEntity<UsuarioDto> obtenerUsuario(@PathVariable Long funcionarioId,
                                                   @PathVariable String fecha) {
        LocalDate fechaDate = LocalDate.parse(fecha);
        Optional<Usuario> usuario = obtenerUsuarioPorTurnoAsignadoUseCase.obtenerUsuarioPorTurnoAsignado(funcionarioId, fechaDate);
        return usuario.map(u -> ResponseEntity.ok(usuarioMapper.toDto(u)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionTurnoDto> editar(@PathVariable Long id,
                                                     @RequestBody EditarTurnoAsignadoRequest request) {
        AsignacionTurno asignacion = editarTurnoAsignadoUseCase.editarTurnoAsignado(
                id,
                request.getNuevoFuncionarioId(),
                request.getNuevaFecha(),
                request.getNuevoTurnoId()
        );
        AsignacionTurnoDto dto = asignacionTurnoMapper.toDto(asignacion);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/fecha/{fecha}")
    public List<AsignacionTurno> listarPorFecha(@PathVariable String fecha) {

    LocalDate fechaConsulta = LocalDate.parse(fecha);

    return listarAsignacionesPorFecha.listarAsignacionesPorFecha(fechaConsulta);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTurnoAsignadoUseCase.eliminarTurnoAsignado(id);
        return ResponseEntity.noContent().build();
    }
}

class AsignarTurnoRequest {
    private Long funcionarioId;
    private Long turnoId;
    private Long areaId;
    private LocalDate fecha;

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public Long getTurnoId() { return turnoId; }
    public void setTurnoId(Long turnoId) { this.turnoId = turnoId; }

    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }

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