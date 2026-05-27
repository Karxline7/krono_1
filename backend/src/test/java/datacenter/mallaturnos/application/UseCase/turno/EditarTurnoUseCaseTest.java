package datacenter.mallaturnos.application.UseCase.turno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.application.UseCase.turno.EditarTurnoUseCase;
import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditarTurnoUseCaseTest {

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private EditarTurnoUseCase editarTurnoUseCase;

    private TurnoDto turnoDto;
    private Turno turno;

    @BeforeEach
    void setUp() {
        turnoDto = new TurnoDto();
        turnoDto.setNombre("Turno Editado");
        turnoDto.setHoraInicio(LocalTime.of(8, 0));
        turnoDto.setHoraFin(LocalTime.of(17, 0));
        turnoDto.setHoraalmuerzo(LocalTime.of(12, 0));
        turnoDto.setHorabreak(LocalTime.of(15, 0));

        turno = new Turno();
        turno.setId(1L);
        turno.setNombre("Turno Viejo");
        turno.setHoraInicio(LocalTime.of(7, 0));
        turno.setHoraFin(LocalTime.of(16, 0));
        turno.setHoraalmuerzo(LocalTime.of(11, 0));
        turno.setHorabreak(LocalTime.of(14, 0));
    }

    @Test
    void editarTurno_correctamente() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(turno)).thenReturn(turno);
        when(turnoMapper.toDto(turno)).thenReturn(turnoDto);

        TurnoDto resultado = editarTurnoUseCase.editarTurno(1L, turnoDto);

        assertNotNull(resultado);
        assertEquals("Turno Editado", resultado.getNombre());

        verify(turnoRepository).findById(1L);
        verify(turnoRepository).save(turno);
        verify(turnoMapper).toDto(turno);
    }

    @Test
    void editarTurno_noExiste() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> editarTurnoUseCase.editarTurno(1L, turnoDto)
        );

        assertEquals("Turno no encontrado con ID: 1", exception.getMessage());

        verify(turnoRepository).findById(1L);
        verify(turnoRepository, never()).save(any());
    }

    @Test
    void editarTurno_horaInicioMayorHoraFin() {
        turnoDto.setHoraInicio(LocalTime.of(18, 0));
        turnoDto.setHoraFin(LocalTime.of(17, 0));

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> editarTurnoUseCase.editarTurno(1L, turnoDto)
        );

        assertEquals("La hora de inicio debe ser menor que la hora de fin", exception.getMessage());
    }

    @Test
    void editarTurno_almuerzoFueraDeTurno() {
        turnoDto.setHoraalmuerzo(LocalTime.of(7, 0));

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> editarTurnoUseCase.editarTurno(1L, turnoDto)
        );

        assertEquals("La hora de almuerzo debe estar dentro del turno", exception.getMessage());
    }

    @Test
    void editarTurno_breakFueraDeTurno() {
        turnoDto.setHorabreak(LocalTime.of(18, 0));

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> editarTurnoUseCase.editarTurno(1L, turnoDto)
        );

        assertEquals("La hora de break debe estar dentro del turno", exception.getMessage());
    }
}
