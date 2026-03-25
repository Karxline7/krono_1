package com.datacenter.mallaturnos.infrastructure.controller.Rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.application.UseCase.Rol.*;
import com.datacenter.mallaturnos.application.Dto.Rol.CrearRolRequest;
import com.datacenter.mallaturnos.application.Dto.Rol.EditarRolRequest;

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
    public ResponseEntity<RolDto> crear(@RequestBody CrearRolRequest request) {

        RolDto dto = new RolDto(
                null,
                request.getNombre(),
                request.getDescripcion()
        );

        RolDto rolCreado = crearRolUseCase.crearRol(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDto> obtener(@PathVariable Long id) {

        Optional<RolDto> rol = obtenerRolUseCase.obtenerRol(id);

        return rol
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RolDto>> listar() {

        List<RolDto> roles = listarRolesUseCase.listarRoles();

        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDto> editar(@PathVariable Long id,
                                         @RequestBody EditarRolRequest request) {

        RolDto dto = new RolDto(
                id,
                request.getNombre(),
                request.getDescripcion()
        );

        RolDto rolActualizado = editarRolUseCase.editarRol(id, dto);

        return ResponseEntity.ok(rolActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        eliminarRolUseCase.eliminarRol(id);

        return ResponseEntity.noContent().build();
    }
}