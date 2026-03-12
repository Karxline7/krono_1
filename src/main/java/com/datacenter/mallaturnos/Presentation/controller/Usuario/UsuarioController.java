package com.datacenter.mallaturnos.Presentation.controller.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.application.UseCase.Usuario.*;
import com.datacenter.mallaturnos.Presentation.Dto.UsuarioDto;
import com.datacenter.mallaturnos.Presentation.mappers.UsuarioMapper;
import com.datacenter.mallaturnos.port.in.Usuario.CrearUsuarioUseCasePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final CrearUsuarioUseCasePort crearUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(CrearUsuarioUseCasePort crearUsuarioUseCase,
                             ObtenerUsuarioUseCase obtenerUsuarioUseCase,
                             ListarUsuariosUseCase listarUsuariosUseCase,
                             EditarUsuarioUseCase editarUsuarioUseCase,
                             EliminarUsuarioUseCase eliminarUsuarioUseCase,
                             UsuarioMapper usuarioMapper) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.obtenerUsuarioUseCase = obtenerUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> crear(@RequestBody CrearUsuarioRequest request) {
        Usuario usuario = crearUsuarioUseCase.crearUsuario(
                request.getNombre(),
                request.getTipoDocumento(),
                request.getNumeroDocumento(),
                request.getContrasena(),
                request.getRolId(),
                request.getCargoId(),
                request.getAreaId()
        );
        UsuarioDto dto = usuarioMapper.toDto(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtener(@PathVariable Long id) {

    Optional<Usuario> usuario = obtenerUsuarioUseCase.obtenerUsuario(id);

    if (usuario.isPresent()) {
        UsuarioDto dto = usuarioMapper.toDto(usuario.get());
        return ResponseEntity.ok(dto);
    }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<UsuarioDto>> listarPorArea(@PathVariable Long areaId) {

    List<Usuario> usuarios = listarUsuariosUseCase.listarPorArea(areaId);

    List<UsuarioDto> dtos = new ArrayList<>();

        if (usuarios != null) {
            for (Usuario u : usuarios) {
            dtos.add(usuarioMapper.toDto(u));
            }
        }

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/area/{areaId}/rol/{rolId}")
public ResponseEntity<List<UsuarioDto>> listarPorAreaYRol(@PathVariable Long areaId,
                                                          @PathVariable Long rolId) {

    List<Usuario> usuarios = listarUsuariosUseCase.listarPorAreaYRol(areaId, rolId);
    List<UsuarioDto> dtos = new ArrayList<>();

        if (usuarios != null) {
            for (Usuario u : usuarios) {
            dtos.add(usuarioMapper.toDto(u));
            }
        }
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> editar(@PathVariable Long id,
                                             @RequestBody EditarUsuarioRequest request) {
        Usuario usuario = editarUsuarioUseCase.editarUsuario(
                id,
                request.getNombre(),
                request.getTipoDocumento(),
                request.getContrasena(),
                request.getRolId(),
                request.getCargoId(),
                request.getAreaId()
        );
        UsuarioDto dto = usuarioMapper.toDto(usuario);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarUsuarioUseCase.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

class CrearUsuarioRequest {
    private String nombre;
    private String tipoDocumento;
    private Integer numeroDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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
    private String nombre;
    private String tipoDocumento;
    private Integer contrasena;
    private Long rolId;
    private Long cargoId;
    private Long areaId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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