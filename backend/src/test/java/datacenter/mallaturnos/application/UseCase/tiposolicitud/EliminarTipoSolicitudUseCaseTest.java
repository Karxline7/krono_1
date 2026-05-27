package datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.UseCase.tiposolicitud.EliminarTipoSolicitudUseCase;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EliminarTipoSolicitudUseCaseTest {

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @InjectMocks
    private EliminarTipoSolicitudUseCase useCase;

    @Test
    void deberiaEliminarTipoSolicitudCorrectamente() {

        Long id = 1L;

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.of(new TipoSolicitud()));

        boolean resultado = useCase.eliminarTipoSolicitud(id);

        assertTrue(resultado);

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudRepository).delete(id);
    }

    @Test
    void deberiaLanzarExcepcionSiTipoSolicitudNoExiste() {

        Long id = 1L;

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.eliminarTipoSolicitud(id);
        });

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudRepository, never()).delete(any());
    }
}
