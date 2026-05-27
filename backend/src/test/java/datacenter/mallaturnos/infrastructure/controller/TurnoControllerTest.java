package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.application.Dto.Turno.Request.CrearTurnoRequest;
import com.datacenter.krono_12.application.Dto.Turno.Request.EditarTurnoRequest;
import com.datacenter.krono_12.application.UseCase.turno.*;
import com.datacenter.krono_12.infrastructure.controller.Turno.TurnoController;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TurnoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CrearTurnoUseCase crearTurnoUseCase;
    private ObtenerTurnoUseCase obtenerTurnoUseCase;
    private ListarTurnosUseCase listarTurnosUseCase;
    private EditarTurnoUseCase editarTurnoUseCase;
    private EliminarTurnoUseCase eliminarTurnoUseCase;
    private TurnoMapper turnoMapper;

    @BeforeEach
    void setUp() {
        crearTurnoUseCase = Mockito.mock(CrearTurnoUseCase.class);
        obtenerTurnoUseCase = Mockito.mock(ObtenerTurnoUseCase.class);
        listarTurnosUseCase = Mockito.mock(ListarTurnosUseCase.class);
        editarTurnoUseCase = Mockito.mock(EditarTurnoUseCase.class);
        eliminarTurnoUseCase = Mockito.mock(EliminarTurnoUseCase.class);
        turnoMapper = Mockito.mock(TurnoMapper.class);

        TurnoController controller = new TurnoController(
                crearTurnoUseCase,
                obtenerTurnoUseCase,
                listarTurnosUseCase,
                editarTurnoUseCase,
                eliminarTurnoUseCase,
                turnoMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void crearTurno_deberiaRetornar201() throws Exception {
        CrearTurnoRequest request = new CrearTurnoRequest();
        request.setNombre("Turno Mañana");
        request.setHoraInicio(LocalTime.of(8, 0));
        request.setHoraFin(LocalTime.of(17, 0));
        request.setHoraAlmuerzo(LocalTime.of(12, 0));
        request.setHoraBreak(LocalTime.of(10, 0));

        TurnoDto dto = new TurnoDto(
                1L,
                "Turno Mañana",
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                LocalTime.of(10, 0)
        );

        Mockito.when(crearTurnoUseCase.crearTurno(Mockito.any(TurnoDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerTurno_existente_deberiaRetornar200() throws Exception {
        TurnoDto dto = new TurnoDto(
                1L,
                "Turno Mañana",
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                LocalTime.of(12, 0),
                LocalTime.of(10, 0)
        );

        Mockito.when(obtenerTurnoUseCase.obtenerTurno(1L))
                .thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/turnos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTurno_noExistente_deberiaRetornar404() throws Exception {
        Mockito.when(obtenerTurnoUseCase.obtenerTurno(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/turnos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTurnos_deberiaRetornar200() throws Exception {
        List<TurnoDto> lista = List.of(
                new TurnoDto(
                        1L,
                        "Turno Mañana",
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0),
                        LocalTime.of(12, 0),
                        LocalTime.of(10, 0)
                )
        );

        Mockito.when(listarTurnosUseCase.listarTurnos())
                .thenReturn(lista);

        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk());
    }

    @Test
    void editarTurno_deberiaRetornar200() throws Exception {
        EditarTurnoRequest request = new EditarTurnoRequest();
        request.setNombre("Turno Tarde");
        request.setHoraInicio(LocalTime.of(9, 0));
        request.setHoraFin(LocalTime.of(18, 0));
        request.setHoraalmuerzo(LocalTime.of(13, 0));
        request.setHorabreak(LocalTime.of(11, 0));

        TurnoDto dto = new TurnoDto(
                1L,
                "Turno Tarde",
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                LocalTime.of(13, 0),
                LocalTime.of(11, 0)
        );

        Mockito.when(editarTurnoUseCase.editarTurno(Mockito.eq(1L), Mockito.any(TurnoDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/turnos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarTurno_deberiaRetornar204() throws Exception {
        Mockito.when(eliminarTurnoUseCase.eliminarTurno(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/turnos/1"))
                .andExpect(status().isNoContent());
    }
}
