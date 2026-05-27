package datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.application.UseCase.tiposolicitud.ObtenerTipoSolicitudUseCase;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObtenerTipoSolicitudUseCaseTest {

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @Mock
    private TipoSolicitudMapper tipoSolicitudMapper;

    @InjectMocks
    private ObtenerTipoSolicitudUseCase useCase;

    @Test
    void deberiaObtenerTipoSolicitudCorrectamente() {

        Long id = 1L;

        TipoSolicitud tipo = new TipoSolicitud();
        TipoSolicitudDto dto = new TipoSolicitudDto();

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.of(tipo));

        when(tipoSolicitudMapper.toDto(tipo))
                .thenReturn(dto);

        Optional<TipoSolicitudDto> resultado = useCase.obtenerTipoSolicitud(id);

        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get());

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudMapper).toDto(tipo);
    }

    @Test
    void deberiaRetornarVacioSiTipoSolicitudNoExiste() {

        Long id = 1L;

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<TipoSolicitudDto> resultado = useCase.obtenerTipoSolicitud(id);

        assertFalse(resultado.isPresent());

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudMapper, never()).toDto(any());
    }
}
