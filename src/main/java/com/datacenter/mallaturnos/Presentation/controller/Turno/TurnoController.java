package com.datacenter.mallaturnos.Presentation.controller.Turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.application.UseCase.turno.*;
import com.datacenter.mallaturnos.Presentation.Dto.TurnoDto;
import com.datacenter.mallaturnos.Presentation.mappers.TurnoMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final CrearTurnoUseCase crearTurnoUseCase;
    private final ObtenerTurnoUseCase obtenerTurnoUseCase;
    private final ListarTurnosUseCase listarTurnosUseCase;
    private final EditarTurnoUseCase editarTurnoUseCase;
    private final EliminarTurnoUseCase eliminarTurnoUseCase;
    private final TurnoMapper turnoMapper;

    public TurnoController(CrearTurnoUseCase crearTurnoUseCase,
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
        this.turnoMapper = turnoMapper;
    }

    @PostMapping
    public ResponseEntity<TurnoDto> crear(@RequestBody CrearTurnoRequest request) {
        Turno turno = crearTurnoUseCase.crearTurno(
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraalmuerzo(),
                request.getHorabreak()
        );
        TurnoDto dto = turnoMapper.toDto(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> obtener(@PathVariable Long id) {
        Optional<Turno> turno = obtenerTurnoUseCase.obtenerTurno(id);
        return turno.map(t -> ResponseEntity.ok(turnoMapper.toDto(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TurnoDto>> listar() {
        List<Turno> turnos = listarTurnosUseCase.listarTurnos();
        List<TurnoDto> dtos = turnos.stream()
                .map(turnoMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDto> editar(@PathVariable Long id,
                                           @RequestBody EditarTurnoRequest request) {
        Turno turno = editarTurnoUseCase.editarTurno(
                id,
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraalmuerzo(),
                request.getHorabreak()
        );
        TurnoDto dto = turnoMapper.toDto(turno);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTurnoUseCase.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }
}

class CrearTurnoRequest {
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaalmuerzo;
    private LocalTime horabreak;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public LocalTime getHoraalmuerzo() { return horaalmuerzo; }
    public void setHoraalmuerzo(LocalTime horaalmuerzo) { this.horaalmuerzo = horaalmuerzo; }

    public LocalTime getHorabreak() { return horabreak; }
    public void setHorabreak(LocalTime horabreak) { this.horabreak = horabreak; }
}

class EditarTurnoRequest {
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaalmuerzo;
    private LocalTime horabreak;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public LocalTime getHoraalmuerzo() { return horaalmuerzo; }
    public void setHoraalmuerzo(LocalTime horaalmuerzo) { this.horaalmuerzo = horaalmuerzo; }

    public LocalTime getHorabreak() { return horabreak; }
    public void setHorabreak(LocalTime horabreak) { this.horabreak = horabreak; }
}