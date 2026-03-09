package com.datacenter.mallaturnos.Presentation.http.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.application.UseCase.Rol.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final CrearRolUseCase crearRolUseCase;
    private final ObtenerRolUseCase obtenerRolUseCase;
    private final ListarRolesUseCase listarRolesUseCase;
    private final EditarRolUseCase editarRolUseCase;
    private final EliminarRolUseCase eliminarRolUseCase;

    public RolController(CrearRolUseCase crearRolUseCase,
                         ObtenerRolUseCase obtenerRolUseCase,
                         ListarRolesUseCase listarRolesUseCase,
                         EditarRolUseCase editarRolUseCase,
                         EliminarRolUseCase eliminarRolUseCase) {
        this.crearRolUseCase = crearRolUseCase;
        this.obtenerRolUseCase = obtenerRolUseCase;
        this.listarRolesUseCase = listarRolesUseCase;
        this.editarRolUseCase = editarRolUseCase;
        this.eliminarRolUseCase = eliminarRolUseCase;
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody CrearRolRequest request) {
        Rol rol = crearRolUseCase.ejecutar(request.getNombre(), request.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtener(@PathVariable Long id) {
        Optional<Rol> rol = obtenerRolUseCase.ejecutar(id);
        return rol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> roles = listarRolesUseCase.ejecutar();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> editar(@PathVariable Long id,
                                      @RequestBody EditarRolRequest request) {
        Rol rol = editarRolUseCase.ejecutar(id, request.getNombre(), request.getDescripcion());
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarRolUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}

class CrearRolRequest {
    private String nombre;
    private String descripcion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

class EditarRolRequest {
    private String nombre;
    private String descripcion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
