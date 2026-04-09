package datacenter.mallaturnos.application.UseCase.Solicitud;

import com.datacenter.mallaturnos.application.UseCase.Solicitud.EliminarSolicitudUseCase;
import com.datacenter.mallaturnos.infrastructure.port.out.SolicitudRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EliminarSolicitudUseCaseTest {

    @Mock
    private SolicitudRepositoryPort solicitudTurnoRepositoryPort;

    @InjectMocks
    private EliminarSolicitudUseCase useCase;

    // ❌ No existe la solicitud
    @Test
    void deberiaLanzarExcepcionSiNoExisteSolicitud() {

        Long id = 1L;

        when(solicitudTurnoRepositoryPort.existsById(id))
                .thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            useCase.eliminarSolicitud(id);
        });

        verify(solicitudTurnoRepositoryPort).existsById(id);
        verify(solicitudTurnoRepositoryPort, never()).deleteById(anyLong());
    }

    // ✅ Eliminar correctamente
    @Test
    void deberiaEliminarSolicitudCorrectamente() {

        Long id = 1L;

        when(solicitudTurnoRepositoryPort.existsById(id))
                .thenReturn(true);

        useCase.eliminarSolicitud(id);

        verify(solicitudTurnoRepositoryPort).existsById(id);
        verify(solicitudTurnoRepositoryPort).deleteById(id);
    }
}
