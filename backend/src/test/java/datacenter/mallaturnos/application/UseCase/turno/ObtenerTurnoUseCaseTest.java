package datacenter.mallaturnos.application.UseCase.turno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.application.UseCase.turno.ObtenerTurnoUseCase;
import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObtenerTurnoUseCaseTest {

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private ObtenerTurnoUseCase obtenerTurnoUseCase;

    @Test
    void obtenerTurno_existe() {
        Turno turno = new Turno();
        turno.setId(1L);

        TurnoDto dto = new TurnoDto();
        dto.setNombre("Turno Mañana");
        dto.setHoraInicio(LocalTime.of(8, 0));

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoMapper.toDto(turno)).thenReturn(dto);

        Optional<TurnoDto> resultado = obtenerTurnoUseCase.obtenerTurno(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Turno Mañana", resultado.get().getNombre());

        verify(turnoRepository).findById(1L);
        verify(turnoMapper).toDto(turno);
    }

    @Test
    void obtenerTurno_noExiste() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TurnoDto> resultado = obtenerTurnoUseCase.obtenerTurno(1L);

        assertFalse(resultado.isPresent());

        verify(turnoRepository).findById(1L);
        verify(turnoMapper, never()).toDto(any());
    }
}