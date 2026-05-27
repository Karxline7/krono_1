package com.datacenter.krono_12.infrastructure.controller.Usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datacenter.krono_12.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.Request.CrearUsuarioRequest;
import com.datacenter.krono_12.application.UseCase.Usuario.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;

    public UsuarioController(
            CrearUsuarioUseCase crearUsuarioUseCase,
            ObtenerUsuarioUseCase obtenerUsuarioUseCase,
            ListarUsuariosUseCase listarUsuariosUseCase,
            EditarUsuarioUseCase editarUsuarioUseCase,
            EliminarUsuarioUseCase eliminarUsuarioUseCase
    ) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> crear(@RequestBody CrearUsuarioRequest request) {

        CrearUsuarioDto dto = new CrearUsuarioDto(
                request.getNombre(),
                request.getTipoDocumento(),
                request.getNumeroDocumento(),
                request.getContrasena(),
                request.getRolId(),
                request.getCargoId(),
                request.getAreaId()
        );

        UsuarioDto usuario = crearUsuarioUseCase.crearUsuario(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtener(@PathVariable Long id) {

        return obtenerUsuarioUseCase.obtenerUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<UsuarioDto> listarUsuarios() {
        return listarUsuariosUseCase.listar();
    }
    
    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<UsuarioDto>> listarPorArea(@PathVariable Long areaId) {

        List<UsuarioDto> usuarios = listarUsuariosUseCase.listarPorArea(areaId);

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/area/{areaId}/rol/{rolId}")
    public ResponseEntity<List<UsuarioDto>> listarPorAreaYRol(
            @PathVariable Long areaId,
            @PathVariable Long rolId) {

        List<UsuarioDto> usuarios = listarUsuariosUseCase.listarPorAreaYRol(areaId, rolId);

        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> editar(
            @PathVariable Long id,
            @RequestBody EditarUsuarioDto dto) {

        UsuarioDto usuario = editarUsuarioUseCase.editarUsuario(dto);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        eliminarUsuarioUseCase.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}