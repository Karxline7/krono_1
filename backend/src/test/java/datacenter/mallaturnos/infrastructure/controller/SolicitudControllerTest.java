package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request.AprobarSolicitudRequest;
import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.Request.DenegarSolicitudRequest;
import com.datacenter.mallaturnos.application.UseCase.Solicitud.*;
import com.datacenter.mallaturnos.infrastructure.controller.Solicitud.SolicitudController;
import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SolicitudControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private SolicitudTurnoUseCase solicitudTurnoUseCase;
    private AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private DenegarSolicitudUseCase denegarSolicitudUseCase;
    private ListarSolicitudesUseCase listarSolicitudesUseCase;
    private SolicitudTurnoMapper solicitudTurnoMapper;

    @BeforeEach
    void setUp() {
        solicitudTurnoUseCase = Mockito.mock(SolicitudTurnoUseCase.class);
        aprobarSolicitudUseCase = Mockito.mock(AprobarSolicitudUseCase.class);
        denegarSolicitudUseCase = Mockito.mock(DenegarSolicitudUseCase.class);
        listarSolicitudesUseCase = Mockito.mock(ListarSolicitudesUseCase.class);
        solicitudTurnoMapper = Mockito.mock(SolicitudTurnoMapper.class);

        SolicitudController controller = new SolicitudController(
                solicitudTurnoUseCase,
                aprobarSolicitudUseCase,
                denegarSolicitudUseCase,
                listarSolicitudesUseCase,
                solicitudTurnoMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void solicitarTurno_deberiaRetornar201() throws Exception {
        SolicitudTurnoDto dto = new SolicitudTurnoDto();

        Mockito.when(solicitudTurnoUseCase.crearSolicitudTurno(Mockito.any(SolicitudTurnoDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/solicitudes/solicitud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void listarSolicitudes_deberiaRetornar200() throws Exception {
        List<SolicitudTurnoDto> lista = List.of(new SolicitudTurnoDto());

        Mockito.when(listarSolicitudesUseCase.listarSolicitudes())
                .thenReturn(lista);

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk());
    }

    @Test
    void aprobarSolicitud_deberiaRetornar200() throws Exception {
        AprobarSolicitudRequest request = new AprobarSolicitudRequest();
        SolicitudTurnoDto dto = new SolicitudTurnoDto();

        Mockito.when(aprobarSolicitudUseCase.aprobarSolicitud(1L))
                .thenReturn(dto);

        mockMvc.perform(put("/api/solicitudes/1/aprobar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void denegarSolicitud_deberiaRetornar200() throws Exception {
        DenegarSolicitudRequest request = new DenegarSolicitudRequest();
        SolicitudTurnoDto dto = new SolicitudTurnoDto();

        Mockito.when(denegarSolicitudUseCase.denegarSolicitud(1L))
                .thenReturn(dto);

        mockMvc.perform(put("/api/solicitudes/1/denegar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
