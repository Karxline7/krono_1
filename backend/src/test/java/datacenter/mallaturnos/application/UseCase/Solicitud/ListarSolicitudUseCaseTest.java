package datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.mallaturnos.application.UseCase.Solicitud.ListarSolicitudesUseCase;
import com.datacenter.mallaturnos.domain.model.SolicitudTurno;
import com.datacenter.mallaturnos.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarSolicitudesUseCaseTest {

    @Mock
    private SolicitudRepositoryPort repository;

    @Mock
    private SolicitudTurnoMapper mapper;

    @InjectMocks
    private ListarSolicitudesUseCase useCase;

    // ✅ Listar solicitudes correctamente
    @Test
    void deberiaListarSolicitudesCorrectamente() {

        SolicitudTurno s1 = new SolicitudTurno();
        SolicitudTurno s2 = new SolicitudTurno();

        SolicitudTurnoDto dto1 = new SolicitudTurnoDto();
        SolicitudTurnoDto dto2 = new SolicitudTurnoDto();

        when(repository.obtener())
                .thenReturn(List.of(s1, s2));

        when(mapper.toDto(any(SolicitudTurno.class)))
                .thenReturn(dto1, dto2);

        List<SolicitudTurnoDto> resultado = useCase.listarSolicitudes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository).obtener();
        verify(mapper, times(2)).toDto(any(SolicitudTurno.class));
    }

    // ✅ Lista vacía
    @Test
    void deberiaRetornarListaVaciaSiNoHaySolicitudes() {

        when(repository.obtener())
                .thenReturn(List.of());

        List<SolicitudTurnoDto> resultado = useCase.listarSolicitudes();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository).obtener();
        verify(mapper, never()).toDto(any());
    }
}