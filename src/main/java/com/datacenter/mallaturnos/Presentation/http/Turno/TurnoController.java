package com.datacenter.mallaturnos.Presentation.http.Turno;

import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.application.turno.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    public TurnoController(CrearTurnoUseCase crearTurnoUseCase,
                           ObtenerTurnoUseCase obtenerTurnoUseCase,
                           ListarTurnosUseCase listarTurnosUseCase,
                           EditarTurnoUseCase editarTurnoUseCase,
                           EliminarTurnoUseCase eliminarTurnoUseCase) {
        this.crearTurnoUseCase = crearTurnoUseCase;
        this.obtenerTurnoUseCase = obtenerTurnoUseCase;
        this.listarTurnosUseCase = listarTurnosUseCase;
        this.editarTurnoUseCase = editarTurnoUseCase;
        this.eliminarTurnoUseCase = eliminarTurnoUseCase;
    }

    @PostMapping
    public ResponseEntity<Turno> crear(@RequestBody CrearTurnoRequest request) {
        Turno turno = crearTurnoUseCase.ejecutar(
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraalmuerzo(),
                request.getHorabreak()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(turno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtener(@PathVariable Long id) {
        Optional<Turno> turno = obtenerTurnoUseCase.ejecutar(id);
        return turno.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listar() {
        List<Turno> turnos = listarTurnosUseCase.ejecutar();
        return ResponseEntity.ok(turnos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> editar(@PathVariable Long id,
                                        @RequestBody EditarTurnoRequest request) {
        Turno turno = editarTurnoUseCase.ejecutar(
                id,
                request.getNombre(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getHoraalmuerzo(),
                request.getHorabreak()
        );
        return ResponseEntity.ok(turno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTurnoUseCase.ejecutar(id);
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