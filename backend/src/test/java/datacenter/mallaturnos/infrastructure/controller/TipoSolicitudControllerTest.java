package datacenter.mallaturnos.infrastructure.controller;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.application.Dto.TipoSolicitud.Request.CrearTipoSolicitudRequest;
import com.datacenter.krono_12.application.Dto.TipoSolicitud.Request.EditarTipoSolicitudRequest;
import com.datacenter.krono_12.application.UseCase.tiposolicitud.*;
import com.datacenter.krono_12.infrastructure.controller.TipoSolicitud.TipoSolicitudController;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
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

public class TipoSolicitudControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CrearTipoSolicitudUseCase crearTipoSolicitudUseCase;
    private ObtenerTipoSolicitudUseCase obtenerTipoSolicitudUseCase;
    private ListarTipoSolicitudUseCase listarTiposSolicitudUseCase;
    private EditarTipoSolicitudUseCase editarTipoSolicitudUseCase;
    private EliminarTipoSolicitudUseCase eliminarTipoSolicitudUseCase;
    private TipoSolicitudMapper tipoSolicitudMapper;

    @BeforeEach
    void setUp() {
        crearTipoSolicitudUseCase = Mockito.mock(CrearTipoSolicitudUseCase.class);
        obtenerTipoSolicitudUseCase = Mockito.mock(ObtenerTipoSolicitudUseCase.class);
        listarTiposSolicitudUseCase = Mockito.mock(ListarTipoSolicitudUseCase.class);
        editarTipoSolicitudUseCase = Mockito.mock(EditarTipoSolicitudUseCase.class);
        eliminarTipoSolicitudUseCase = Mockito.mock(EliminarTipoSolicitudUseCase.class);
        tipoSolicitudMapper = Mockito.mock(TipoSolicitudMapper.class);

        TipoSolicitudController controller = new TipoSolicitudController(
                crearTipoSolicitudUseCase,
                obtenerTipoSolicitudUseCase,
                listarTiposSolicitudUseCase,
                editarTipoSolicitudUseCase,
                eliminarTipoSolicitudUseCase,
                tipoSolicitudMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    void crearTipoSolicitud_deberiaRetornar201() throws Exception {
        CrearTipoSolicitudRequest request = new CrearTipoSolicitudRequest();
        request.setNombre("Vacaciones");
        request.setDescripcion("Solicitud de vacaciones");

        TipoSolicitudDto dto = new TipoSolicitudDto(1L, "Vacaciones", "Solicitud de vacaciones");

        Mockito.when(crearTipoSolicitudUseCase.crearTipoSolicitud(Mockito.any(TipoSolicitudDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/tipos-solicitud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerTipoSolicitud_existente_deberiaRetornar200() throws Exception {
        TipoSolicitudDto dto = new TipoSolicitudDto(1L, "Vacaciones", "Solicitud de vacaciones");

        Mockito.when(obtenerTipoSolicitudUseCase.obtenerTipoSolicitud(1L))
                .thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/tipos-solicitud/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTipoSolicitud_noExistente_deberiaRetornar404() throws Exception {
        Mockito.when(obtenerTipoSolicitudUseCase.obtenerTipoSolicitud(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tipos-solicitud/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarTiposSolicitud_deberiaRetornar200() throws Exception {
        List<TipoSolicitudDto> lista = List.of(
                new TipoSolicitudDto(1L, "Vacaciones", "Solicitud de vacaciones")
        );

        Mockito.when(listarTiposSolicitudUseCase.listarTiposSolicitud())
                .thenReturn(lista);

        mockMvc.perform(get("/api/tipos-solicitud"))
                .andExpect(status().isOk());
    }

    @Test
    void editarTipoSolicitud_deberiaRetornar200() throws Exception {
        EditarTipoSolicitudRequest request = new EditarTipoSolicitudRequest();
        request.setNombre("Permiso");
        request.setDescripcion("Permiso personal");

        TipoSolicitudDto dto = new TipoSolicitudDto(1L, "Permiso", "Permiso personal");

        Mockito.when(editarTipoSolicitudUseCase.editarTipoSolicitud(Mockito.eq(1L), Mockito.any(TipoSolicitudDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/tipos-solicitud/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarTipoSolicitud_deberiaRetornar204() throws Exception {
        Mockito.when(eliminarTipoSolicitudUseCase.eliminarTipoSolicitud(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/tipos-solicitud/1"))
                .andExpect(status().isNoContent());
    }
}
