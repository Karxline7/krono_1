package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.krono_12.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.krono_12.application.Dto.AsignacionTurno.Request.AsignarTurnoRequest;
import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.UseCase.asignacion.*;
import com.datacenter.krono_12.infrastructure.controller.Asignacion.AsignacionController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AsignacionControllerTest {

    private MockMvc mockMvc;

    private AsignarTurnoUseCase asignarTurnoUseCase = Mockito.mock(AsignarTurnoUseCase.class);
    private ObtenerUsuarioPorTurnoAsignadoUseCase obtenerUsuarioUseCase = Mockito.mock(ObtenerUsuarioPorTurnoAsignadoUseCase.class);
    private EditarTurnoAsignadoUseCase editarUseCase = Mockito.mock(EditarTurnoAsignadoUseCase.class);
    private EliminarTurnoAsignadoUseCase eliminarUseCase = Mockito.mock(EliminarTurnoAsignadoUseCase.class);
    private ListarAsignacionesPorFechaUseCase listarUseCase = Mockito.mock(ListarAsignacionesPorFechaUseCase.class);

    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @BeforeEach
    void setUp() {
        AsignacionController controller = new AsignacionController(
                asignarTurnoUseCase,
                obtenerUsuarioUseCase,
                editarUseCase,
                eliminarUseCase,
                listarUseCase
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void asignar_turno() throws Exception {
        AsignarTurnoRequest request = new AsignarTurnoRequest();
        request.setFuncionarioId(1L);
        request.setTurnoId(2L);
        request.setFecha(LocalDate.now());

        AsignacionTurnoDto responseDto =
                new AsignacionTurnoDto(1L, 1L, 2L, LocalDate.now());

        when(asignarTurnoUseCase.asignarTurno(Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/asignaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void obtener_usuario() throws Exception {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setId(1L);
        usuario.setNombre("Juan");

        when(obtenerUsuarioUseCase.obtenerUsuarioPorTurnoAsignado(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/asignaciones/funcionario/1/fecha/2025-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_por_fecha() throws Exception {
        when(listarUseCase.listarAsignacionesPorFecha(Mockito.any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/asignaciones/fecha/2025-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_asignacion() throws Exception {
        mockMvc.perform(delete("/api/asignaciones/1"))
                .andExpect(status().isNoContent());
    }
}
