package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.mallaturnos.application.Dto.Rol.CrearRolRequest;
import com.datacenter.mallaturnos.application.Dto.Rol.EditarRolRequest;
import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.application.UseCase.Rol.*;
import com.datacenter.mallaturnos.infrastructure.controller.Rol.RolController;
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

public class RolControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CrearRolUseCase crearRolUseCase;
    private ObtenerRolUseCase obtenerRolUseCase;
    private ListarRolesUseCase listarRolesUseCase;
    private EditarRolUseCase editarRolUseCase;
    private EliminarRolUseCase eliminarRolUseCase;

    @BeforeEach
    void setUp() {
        crearRolUseCase = Mockito.mock(CrearRolUseCase.class);
        obtenerRolUseCase = Mockito.mock(ObtenerRolUseCase.class);
        listarRolesUseCase = Mockito.mock(ListarRolesUseCase.class);
        editarRolUseCase = Mockito.mock(EditarRolUseCase.class);
        eliminarRolUseCase = Mockito.mock(EliminarRolUseCase.class);

        RolController controller = new RolController(
                crearRolUseCase,
                obtenerRolUseCase,
                listarRolesUseCase,
                editarRolUseCase,
                eliminarRolUseCase
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void crearRol_deberiaRetornar201() throws Exception {
        CrearRolRequest request = new CrearRolRequest();
        request.setNombre("ADMIN");
        request.setDescripcion("Administrador");

        RolDto rolDto = new RolDto(1L, "ADMIN", "Administrador");

        Mockito.when(crearRolUseCase.crearRol(Mockito.any(RolDto.class)))
                .thenReturn(rolDto);

        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerRol_existente_deberiaRetornar200() throws Exception {
        RolDto rolDto = new RolDto(1L, "ADMIN", "Administrador");

        Mockito.when(obtenerRolUseCase.obtenerRol(1L))
                .thenReturn(Optional.of(rolDto));

        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerRol_noExistente_deberiaRetornar404() throws Exception {
        Mockito.when(obtenerRolUseCase.obtenerRol(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarRoles_deberiaRetornar200() throws Exception {
        List<RolDto> roles = List.of(
                new RolDto(1L, "ADMIN", "Administrador"),
                new RolDto(2L, "USER", "Usuario")
        );

        Mockito.when(listarRolesUseCase.listarRoles())
                .thenReturn(roles);

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk());
    }

    @Test
    void editarRol_deberiaRetornar200() throws Exception {
        EditarRolRequest request = new EditarRolRequest();
        request.setNombre("ADMIN");
        request.setDescripcion("Administrador editado");

        RolDto rolDto = new RolDto(1L, "ADMIN", "Administrador editado");

        Mockito.when(editarRolUseCase.editarRol(Mockito.eq(1L), Mockito.any(RolDto.class)))
                .thenReturn(rolDto);

        mockMvc.perform(put("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarRol_deberiaRetornar204() throws Exception {
        Mockito.when(eliminarRolUseCase.eliminarRol(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isNoContent());
    }
}
