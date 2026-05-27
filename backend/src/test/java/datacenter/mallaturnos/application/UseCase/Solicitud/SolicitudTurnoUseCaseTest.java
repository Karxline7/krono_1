package datacenter.mallaturnos.application.UseCase.Solicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.application.UseCase.Solicitud.SolicitudTurnoUseCase;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.domain.model.SolicitudTurno;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.SolicitudRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudTurnoUseCaseTest {

    @Mock
    private SolicitudRepositoryPort solicitudRepository;

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @Mock
    private SolicitudTurnoMapper solicitudTurnoMapper;

    @InjectMocks
    private SolicitudTurnoUseCase useCase;

    // ❌ Asignación no existe
    @Test
    void deberiaLanzarExcepcionSiAsignacionNoExiste() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.crearSolicitudTurno(dto);
        });

        verify(asignacionRepository).findById(1L);
        verify(solicitudRepository, never()).save(any());
    }

    // ❌ Tipo solicitud no existe
    @Test
    void deberiaLanzarExcepcionSiTipoSolicitudNoExiste() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.of(new AsignacionTurno()));

        when(tipoSolicitudRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.crearSolicitudTurno(dto);
        });
    }

    // ❌ Ya existe pendiente
    @Test
    void deberiaLanzarExcepcionSiYaExisteSolicitudPendiente() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.of(new AsignacionTurno()));

        when(tipoSolicitudRepository.findById(2L))
                .thenReturn(Optional.of(new TipoSolicitud()));

        when(solicitudRepository.existsPendienteForAsignacion(1L))
                .thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            useCase.crearSolicitudTurno(dto);
        });
    }

    // ❌ Ya existe aprobada
    @Test
    void deberiaLanzarExcepcionSiYaExisteSolicitudAprobada() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.of(new AsignacionTurno()));

        when(tipoSolicitudRepository.findById(2L))
                .thenReturn(Optional.of(new TipoSolicitud()));

        when(solicitudRepository.existsPendienteForAsignacion(1L))
                .thenReturn(false);

        when(solicitudRepository.existsAprobadaForAsignacion(1L))
                .thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            useCase.crearSolicitudTurno(dto);
        });
    }

    // ❌ Ya existe denegada
    @Test
    void deberiaLanzarExcepcionSiYaExisteSolicitudDenegada() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.of(new AsignacionTurno()));

        when(tipoSolicitudRepository.findById(2L))
                .thenReturn(Optional.of(new TipoSolicitud()));

        when(solicitudRepository.existsPendienteForAsignacion(1L))
                .thenReturn(false);

        when(solicitudRepository.existsAprobadaForAsignacion(1L))
                .thenReturn(false);

        when(solicitudRepository.existsDenegadaForAsignacion(1L))
                .thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            useCase.crearSolicitudTurno(dto);
        });
    }

    // ✅ Crear solicitud correctamente
    @Test
    void deberiaCrearSolicitudTurnoCorrectamente() {

        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setAsignacionTurnoId(1L);
        dto.setTipoSolicitudId(2L);

        SolicitudTurno solicitud = new SolicitudTurno();
        SolicitudTurno solicitudGuardada = new SolicitudTurno();
        SolicitudTurnoDto dtoRespuesta = new SolicitudTurnoDto();

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.of(new AsignacionTurno()));

        when(tipoSolicitudRepository.findById(2L))
                .thenReturn(Optional.of(new TipoSolicitud()));

        when(solicitudRepository.existsPendienteForAsignacion(1L))
                .thenReturn(false);

        when(solicitudRepository.existsAprobadaForAsignacion(1L))
                .thenReturn(false);

        when(solicitudRepository.existsDenegadaForAsignacion(1L))
                .thenReturn(false);

        when(solicitudTurnoMapper.toDomain(dto))
                .thenReturn(solicitud);

        when(solicitudRepository.save(any(SolicitudTurno.class)))
                .thenReturn(solicitudGuardada);

        when(solicitudTurnoMapper.toDto(solicitudGuardada))
                .thenReturn(dtoRespuesta);

        SolicitudTurnoDto resultado = useCase.crearSolicitudTurno(dto);

        assertNotNull(resultado);
        verify(solicitudRepository).save(any(SolicitudTurno.class));
        verify(solicitudTurnoMapper).toDto(solicitudGuardada);
    }
}
