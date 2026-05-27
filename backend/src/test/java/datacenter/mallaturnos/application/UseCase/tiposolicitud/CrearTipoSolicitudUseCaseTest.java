package datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.application.UseCase.tiposolicitud.CrearTipoSolicitudUseCase;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearTipoSolicitudUseCaseTest {

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @Mock
    private TipoSolicitudMapper tipoSolicitudMapper;

    @InjectMocks
    private CrearTipoSolicitudUseCase useCase;

    @Test
    void deberiaCrearTipoSolicitudCorrectamente() {

        TipoSolicitudDto dto = new TipoSolicitudDto();
        dto.setNombre("Permiso");

        TipoSolicitud tipoDomain = new TipoSolicitud();
        TipoSolicitud tipoGuardado = new TipoSolicitud();
        TipoSolicitudDto dtoRespuesta = new TipoSolicitudDto();

        when(tipoSolicitudRepository.findByNombre("Permiso"))
                .thenReturn(Optional.empty());

        when(tipoSolicitudMapper.toDomain(dto))
                .thenReturn(tipoDomain);

        when(tipoSolicitudRepository.save(tipoDomain))
                .thenReturn(tipoGuardado);

        when(tipoSolicitudMapper.toDto(tipoGuardado))
                .thenReturn(dtoRespuesta);

        TipoSolicitudDto resultado = useCase.crearTipoSolicitud(dto);

        assertNotNull(resultado);
        verify(tipoSolicitudRepository).save(tipoDomain);
        verify(tipoSolicitudMapper).toDto(tipoGuardado);
    }

    @Test
    void deberiaLanzarExcepcionSiTipoSolicitudYaExiste() {

        TipoSolicitudDto dto = new TipoSolicitudDto();
        dto.setNombre("Permiso");

        when(tipoSolicitudRepository.findByNombre("Permiso"))
                .thenReturn(Optional.of(new TipoSolicitud()));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.crearTipoSolicitud(dto);
        });

        verify(tipoSolicitudRepository, never()).save(any());
    }
}