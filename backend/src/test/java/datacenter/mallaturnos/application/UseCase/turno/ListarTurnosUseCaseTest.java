package datacenter.mallaturnos.application.UseCase.turno;

import com.datacenter.mallaturnos.application.Dto.Turno.TurnoDto;
import com.datacenter.mallaturnos.application.UseCase.turno.ListarTurnosUseCase;
import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.infrastructure.mappers.TurnoMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarTurnosUseCaseTest {

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private ListarTurnosUseCase listarTurnosUseCase;

    @Test
    void listarTurnos_lista() {
        Turno turno1 = new Turno();
        turno1.setId(1L);

        Turno turno2 = new Turno();
        turno2.setId(2L);

        TurnoDto dto1 = new TurnoDto();
        dto1.setNombre("Turno 1");
        dto1.setHoraInicio(LocalTime.of(8, 0));

        TurnoDto dto2 = new TurnoDto();
        dto2.setNombre("Turno 2");
        dto2.setHoraInicio(LocalTime.of(9, 0));

        when(turnoRepository.findAll()).thenReturn(Arrays.asList(turno1, turno2));
        when(turnoMapper.toDto(turno1)).thenReturn(dto1);
        when(turnoMapper.toDto(turno2)).thenReturn(dto2);

        List<TurnoDto> resultado = listarTurnosUseCase.listarTurnos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(turnoRepository).findAll();
        verify(turnoMapper, times(2)).toDto(any());
    }

    @Test
    void listarTurnos_listaVacia() {
        when(turnoRepository.findAll()).thenReturn(Collections.emptyList());

        List<TurnoDto> resultado = listarTurnosUseCase.listarTurnos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(turnoRepository).findAll();
        verify(turnoMapper, never()).toDto(any());
    }
}