package datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.UseCase.asignacion.ListarAsignacionesPorFechaUseCase;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarAsignacionesPorFechaUseCaseTest {

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @Mock
    private AsignacionTurnoMapper mapper;

    @InjectMocks
    private ListarAsignacionesPorFechaUseCase useCase;

    // ✅ Caso con datos
    @Test
    void deberiaListarAsignacionesPorFecha() {

        LocalDate fecha = LocalDate.now();

        AsignacionTurno asignacion = new AsignacionTurno();
        AsignacionTurnoDto dto = new AsignacionTurnoDto();

        when(asignacionRepository.findByFecha(fecha))
                .thenReturn(List.of(asignacion));

        when(mapper.toDto(asignacion))
                .thenReturn(dto);

        List<AsignacionTurnoDto> resultado = useCase.listarAsignacionesPorFecha(fecha);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asignacionRepository).findByFecha(fecha);
        verify(mapper).toDto(asignacion);
    }

    // ✅ Lista vacía
    @Test
    void deberiaRetornarListaVacia() {

        LocalDate fecha = LocalDate.now();

        when(asignacionRepository.findByFecha(fecha))
                .thenReturn(List.of());

        List<AsignacionTurnoDto> resultado = useCase.listarAsignacionesPorFecha(fecha);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(asignacionRepository).findByFecha(fecha);
    }
}