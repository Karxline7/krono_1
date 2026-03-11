package com.datacenter.mallaturnos.Presentation.http.Rol;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.application.UseCase.Rol.*;
import com.datacenter.mallaturnos.Presentation.Dto.RolDto;
import com.datacenter.mallaturnos.Presentation.mappers.RolMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final CrearRolUseCase crearRolUseCase;
    private final ObtenerRolUseCase obtenerRolUseCase;
    private final ListarRolesUseCase listarRolesUseCase;
    private final EditarRolUseCase editarRolUseCase;
    private final EliminarRolUseCase eliminarRolUseCase;
    private final RolMapper rolMapper;

    public RolController(CrearRolUseCase crearRolUseCase,
                         ObtenerRolUseCase obtenerRolUseCase,
                         ListarRolesUseCase listarRolesUseCase,
                         EditarRolUseCase editarRolUseCase,
                         EliminarRolUseCase eliminarRolUseCase,
                         RolMapper rolMapper) {
        this.crearRolUseCase = crearRolUseCase;
        this.obtenerRolUseCase = obtenerRolUseCase;
        this.listarRolesUseCase = listarRolesUseCase;
        this.editarRolUseCase = editarRolUseCase;
        this.eliminarRolUseCase = eliminarRolUseCase;
        this.rolMapper = rolMapper;
    }

    @PostMapping
    public ResponseEntity<RolDto> crear(@RequestBody CrearRolRequest request) {
        Rol rol = crearRolUseCase.ejecutar(request.getNombre(), request.getDescripcion());
        RolDto dto = rolMapper.toDto(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> obtener(@PathVariable Long id) {
        Optional<Rol> rol = obtenerRolUseCase.ejecutar(id);
        return rol.map(r -> ResponseEntity.ok(rolMapper.toDto(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> listar() {
        List<Rol> roles = listarRolesUseCase.ejecutar();
        List<RolDto> dtos = roles.stream()
                .map(rolMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> editar(@PathVariable Long id,
                                      @RequestBody EditarRolRequest request) {
        Rol rol = editarRolUseCase.ejecutar(id, request.getNombre(), request.getDescripcion());
        RolDto dto = rolMapper.toDto(rol);
        return ResponseEntity.ok(dto);
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