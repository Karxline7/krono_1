package datacenter.mallaturnos.application.UseCase.asignacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.UseCase.asignacion.EliminarTurnoAsignadoUseCase;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EliminarTurnoAsignadoUseCaseTest {

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @InjectMocks
    private EliminarTurnoAsignadoUseCase useCase;

    // ✅ Caso exitoso
    @Test
    void deberiaEliminarAsignacion() {

        Long id = 1L;

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.of(new AsignacionTurno()));

        boolean resultado = useCase.eliminarTurnoAsignado(id);

        assertTrue(resultado);
        verify(asignacionRepository).delete(id);
    }

    // ❌ Asignación no existe
    @Test
    void deberiaFallarSiAsignacionNoExiste() {

        Long id = 1L;

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.eliminarTurnoAsignado(id));

        assertEquals("Asignación no encontrada", ex.getMessage());

        verify(asignacionRepository, never()).delete(id);
    }
}
