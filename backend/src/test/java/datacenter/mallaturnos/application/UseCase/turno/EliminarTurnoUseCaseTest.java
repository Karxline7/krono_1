package datacenter.mallaturnos.application.UseCase.turno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.UseCase.turno.EliminarTurnoUseCase;
import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.port.out.TurnoRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EliminarTurnoUseCaseTest {

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @InjectMocks
    private EliminarTurnoUseCase eliminarTurnoUseCase;

    @Test
    void eliminarTurno_correctamente() {
        Turno turno = new Turno();
        turno.setId(1L);

        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        doNothing().when(turnoRepository).delete(1L);

        boolean resultado = eliminarTurnoUseCase.eliminarTurno(1L);

        assertTrue(resultado);

        verify(turnoRepository).findById(1L);
        verify(turnoRepository).delete(1L);
    }

    @Test
    void eliminarTurno_noExiste() {
        when(turnoRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eliminarTurnoUseCase.eliminarTurno(1L)
        );

        assertEquals("Turno no encontrado con ID: 1", exception.getMessage());

        verify(turnoRepository).findById(1L);
        verify(turnoRepository, never()).delete(any());
    }
}
