package com.datacenter.mallaturnos.Presentation.http.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.application.UseCase.Usuario.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase,
                             ObtenerUsuarioUseCase obtenerUsuarioUseCase,
                             ListarUsuariosUseCase listarUsuariosUseCase,
                             EditarUsuarioUseCase editarUsuarioUseCase,
                             EliminarUsuarioUseCase eliminarUsuarioUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;
    }

    // =========================
    // CREAR USUARIO
    // =========================
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody CrearUsuarioRequest request) {
        Usuario usuario = crearUsuarioUseCase.ejecutar(
                request.getNombres(),
                request.getTipoDocumento(),
                request.getNumeroDocumento(),
                request.getContrasena(),
                request.getRolId(),
                request.getCargoId(),
                request.getAreaId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    // =========================
    // OBTENER USUARIO
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        Optional<Usuario> usuario = obtenerUsuarioUseCase.ejecutar(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // =========================
    // LISTAR USUARIOS POR ÁREA
    // =========================
    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<Usuario>> listarPorArea(@PathVariable Long areaId) {
        List<Usuario> usuarios = listarUsuariosUseCase.listarPorArea(areaId);
        return ResponseEntity.ok(usuarios);
    }

    // =========================
    // LISTAR USUARIOS POR ROL
    // =========================
    @GetMapping("/area/{areaId}/rol/{rol}")
    public ResponseEntity<List<Usuario>> listarPorAreaYRol(@PathVariable Long areaId,
                                                           @PathVariable Long rolId) {
        List<Usuario> usuarios = listarUsuariosUseCase.listarPorAreaYRol(areaId, rolId);
        return ResponseEntity.ok(usuarios);
    }

    // =========================
    // EDITAR USUARIO
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable Long id,
                                          @RequestBody EditarUsuarioRequest request) {
        Usuario usuario = editarUsuarioUseCase.ejecutar(
                id,
                request.getNombres(),
                request.getTipoDocumento(),
                request.getContrasena(),
                request.getRolId(),
                request.getCargoId(),
                request.getAreaId(),
                request.getActivo()
        );
        return ResponseEntity.ok(usuario);
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarUsuarioUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}

class CrearUsuarioRequest {
    private String nombres;
    private Integer numeroDocumento;
    private String tipoDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public Integer getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(Integer numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public Integer getContrasena() { return contrasena; }
    public void setContrasena(Integer contrasena) { this.contrasena = contrasena; }

    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }

    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }

    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }
}

class EditarUsuarioRequest {
    private String nombres;
    private String tipoDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;
    private Boolean activo;

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public Integer getContrasena() { return contrasena; }
    public void setContrasena(Integer contrasena) { this.contrasena = contrasena; }

    public Long getRolId() { return rolId; }
    public void setRolId(Long rolId) { this.rolId = rolId; }

    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }

    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}