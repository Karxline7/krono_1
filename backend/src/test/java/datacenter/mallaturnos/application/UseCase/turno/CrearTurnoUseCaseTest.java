package datacenter.mallaturnos.application.UseCase.turno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.application.UseCase.turno.CrearTurnoUseCase;
import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrearTurnoUseCaseTest {

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private TurnoMapper turnoMapper;

    @InjectMocks
    private CrearTurnoUseCase crearTurnoUseCase;

    private TurnoDto turnoDto;
    private Turno turno;

    @BeforeEach
    void setUp() {
        turnoDto = new TurnoDto();
        turnoDto.setNombre("Turno Mañana");
        turnoDto.setHoraInicio(LocalTime.of(8, 0));
        turnoDto.setHoraFin(LocalTime.of(17, 0));
        turnoDto.setHoraalmuerzo(LocalTime.of(12, 0));
        turnoDto.setHorabreak(LocalTime.of(15, 0));

        turno = new Turno();
        turno.setNombre("Turno Mañana");
    }

    @Test
    void crearTurno_correctamente() {
        when(turnoRepository.findByNombre(turnoDto.getNombre())).thenReturn(Optional.empty());
        when(turnoMapper.toDomain(turnoDto)).thenReturn(turno);
        when(turnoRepository.save(turno)).thenReturn(turno);
        when(turnoMapper.toDto(turno)).thenReturn(turnoDto);

        TurnoDto resultado = crearTurnoUseCase.crearTurno(turnoDto);

        assertNotNull(resultado);
        assertEquals("Turno Mañana", resultado.getNombre());

        verify(turnoRepository).findByNombre(turnoDto.getNombre());
        verify(turnoMapper).toDomain(turnoDto);
        verify(turnoRepository).save(turno);
        verify(turnoMapper).toDto(turno);
    }

    @Test
    void crearTurno_yaExiste() {
        when(turnoRepository.findByNombre(turnoDto.getNombre())).thenReturn(Optional.of(turno));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> crearTurnoUseCase.crearTurno(turnoDto)
        );

        assertEquals("Ya existe un turno con el mismo nombre", exception.getMessage());

        verify(turnoRepository).findByNombre(turnoDto.getNombre());
        verify(turnoRepository, never()).save(any());
    }

    @Test
    void crearTurno_horaInicioMayorHoraFin() {
        turnoDto.setHoraInicio(LocalTime.of(18, 0));
        turnoDto.setHoraFin(LocalTime.of(17, 0));

        when(turnoRepository.findByNombre(turnoDto.getNombre())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> crearTurnoUseCase.crearTurno(turnoDto)
        );

        assertEquals("La hora de inicio debe ser antes que la hora de fin", exception.getMessage());
    }

    @Test
    void crearTurno_almuerzoFueraDeTurno() {
        turnoDto.setHoraalmuerzo(LocalTime.of(7, 0));

        when(turnoRepository.findByNombre(turnoDto.getNombre())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> crearTurnoUseCase.crearTurno(turnoDto)
        );

        assertEquals("La hora de almuerzo debe estar entre la hora de inicio y fin", exception.getMessage());
    }

    @Test
    void crearTurno_breakFueraDeTurno() {
        turnoDto.setHorabreak(LocalTime.of(18, 0));

        when(turnoRepository.findByNombre(turnoDto.getNombre())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> crearTurnoUseCase.crearTurno(turnoDto)
        );

        assertEquals("La hora de break debe estar entre la hora de inicio y fin", exception.getMessage());
    }
}
