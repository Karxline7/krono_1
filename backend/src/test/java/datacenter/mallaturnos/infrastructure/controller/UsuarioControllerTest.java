package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.Request.CrearUsuarioRequest;
import com.datacenter.mallaturnos.application.UseCase.Usuario.*;
import com.datacenter.mallaturnos.infrastructure.controller.Usuario.UsuarioController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsuarioControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CrearUsuarioUseCase crearUsuarioUseCase;
    private ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private ListarUsuariosUseCase listarUsuariosUseCase;
    private EditarUsuarioUseCase editarUsuarioUseCase;
    private EliminarUsuarioUseCase eliminarUsuarioUseCase;

    @BeforeEach
    void setUp() {
        crearUsuarioUseCase = Mockito.mock(CrearUsuarioUseCase.class);
        obtenerUsuarioUseCase = Mockito.mock(ObtenerUsuarioUseCase.class);
        listarUsuariosUseCase = Mockito.mock(ListarUsuariosUseCase.class);
        editarUsuarioUseCase = Mockito.mock(EditarUsuarioUseCase.class);
        eliminarUsuarioUseCase = Mockito.mock(EliminarUsuarioUseCase.class);

        UsuarioController controller = new UsuarioController(
                crearUsuarioUseCase,
                obtenerUsuarioUseCase,
                listarUsuariosUseCase,
                editarUsuarioUseCase,
                eliminarUsuarioUseCase
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void crearUsuario_deberiaRetornar201() throws Exception {
        CrearUsuarioRequest request = new CrearUsuarioRequest();
        request.setNombre("Juan");
        request.setTipoDocumento("CC");
        request.setNumeroDocumento(1234567890L);
        request.setContrasena(123456);
        request.setRolId(1L);
        request.setCargoId(1L);
        request.setAreaId(1L);

        UsuarioDto usuario = new UsuarioDto();

        Mockito.when(crearUsuarioUseCase.crearUsuario(Mockito.any(CrearUsuarioDto.class)))
                .thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerUsuario_existente_deberiaRetornar200() throws Exception {
        UsuarioDto usuario = new UsuarioDto();

        Mockito.when(obtenerUsuarioUseCase.obtenerUsuario(1L))
                .thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerUsuario_noExistente_deberiaRetornar404() throws Exception {
        Mockito.when(obtenerUsuarioUseCase.obtenerUsuario(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarUsuarios_deberiaRetornar200() throws Exception {
        List<UsuarioDto> lista = List.of(new UsuarioDto());

        Mockito.when(listarUsuariosUseCase.listar())
                .thenReturn(lista);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void listarUsuariosPorArea_deberiaRetornar200() throws Exception {
        List<UsuarioDto> lista = List.of(new UsuarioDto());

        Mockito.when(listarUsuariosUseCase.listarPorArea(1L))
                .thenReturn(lista);

        mockMvc.perform(get("/api/usuarios/area/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listarUsuariosPorAreaYRol_deberiaRetornar200() throws Exception {
        List<UsuarioDto> lista = List.of(new UsuarioDto());

        Mockito.when(listarUsuariosUseCase.listarPorAreaYRol(1L, 2L))
                .thenReturn(lista);

        mockMvc.perform(get("/api/usuarios/area/1/rol/2"))
                .andExpect(status().isOk());
    }

    @Test
    void editarUsuario_deberiaRetornar200() throws Exception {
        EditarUsuarioDto dto = new EditarUsuarioDto();
        UsuarioDto usuario = new UsuarioDto();

        Mockito.when(editarUsuarioUseCase.editarUsuario(Mockito.any(EditarUsuarioDto.class)))
                .thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarUsuario_deberiaRetornar204() throws Exception {
        Mockito.when(eliminarUsuarioUseCase.eliminarUsuario(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
