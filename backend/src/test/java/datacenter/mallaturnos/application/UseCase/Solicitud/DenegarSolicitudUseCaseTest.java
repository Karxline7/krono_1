package datacenter.mallaturnos.application.UseCase.Solicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.application.UseCase.Solicitud.DenegarSolicitudUseCase;
import com.datacenter.krono_12.domain.model.EstadoSolicitud;
import com.datacenter.krono_12.domain.model.SolicitudTurno;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.SolicitudRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DenegarSolicitudUseCaseTest {

    @Mock
    private SolicitudRepositoryPort solicitudRepository;

    @Mock
    private SolicitudTurnoMapper solicitudTurnoMapper;

    @InjectMocks
    private DenegarSolicitudUseCase useCase;

    // ❌ Solicitud no existe
    @Test
    void deberiaLanzarExcepcionSiSolicitudNoExiste() {

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.denegarSolicitud(1L);
        });

        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository, never()).save(any());
        verify(solicitudTurnoMapper, never()).toDto(any());
    }

    // ❌ Solicitud no está pendiente
    @Test
    void deberiaLanzarExcepcionSiSolicitudNoEstaPendiente() {

        SolicitudTurno solicitud = new SolicitudTurno();
        solicitud.setEstado(EstadoSolicitud.APROBADA);

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.denegarSolicitud(1L);
        });

        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository, never()).save(any());
        verify(solicitudTurnoMapper, never()).toDto(any());
    }

    // ✅ Denegar solicitud correctamente
    @Test
    void deberiaDenegarSolicitudCorrectamente() {

        SolicitudTurno solicitud = new SolicitudTurno();
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        SolicitudTurno solicitudGuardada = new SolicitudTurno();
        solicitudGuardada.setEstado(EstadoSolicitud.DENEGADA);

        SolicitudTurnoDto dto = new SolicitudTurnoDto();

        when(solicitudRepository.findById(1L))
                .thenReturn(Optional.of(solicitud));

        when(solicitudRepository.save(any(SolicitudTurno.class)))
                .thenReturn(solicitudGuardada);

        when(solicitudTurnoMapper.toDto(any(SolicitudTurno.class)))
                .thenReturn(dto);

        SolicitudTurnoDto resultado = useCase.denegarSolicitud(1L);

        assertNotNull(resultado);

        verify(solicitudRepository).findById(1L);
        verify(solicitudRepository).save(any(SolicitudTurno.class));
        verify(solicitudTurnoMapper).toDto(any(SolicitudTurno.class));
    }
}