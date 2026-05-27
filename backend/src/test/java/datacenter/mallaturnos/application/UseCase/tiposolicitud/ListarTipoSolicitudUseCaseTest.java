package datacenter.mallaturnos.application.UseCase.tiposolicitud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.krono_12.application.UseCase.tiposolicitud.ListarTipoSolicitudUseCase;
import com.datacenter.krono_12.domain.model.TipoSolicitud;
import com.datacenter.krono_12.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.krono_12.infrastructure.port.out.TipoSolicitudRepositoryPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarTipoSolicitudUseCaseTest {

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @Mock
    private TipoSolicitudMapper tipoSolicitudMapper;

    @InjectMocks
    private ListarTipoSolicitudUseCase useCase;

    @Test
    void deberiaListarTiposSolicitudCorrectamente() {

        TipoSolicitud tipo1 = new TipoSolicitud();
        TipoSolicitud tipo2 = new TipoSolicitud();

        TipoSolicitudDto dto1 = new TipoSolicitudDto();
        TipoSolicitudDto dto2 = new TipoSolicitudDto();

        when(tipoSolicitudRepository.findAll())
                .thenReturn(List.of(tipo1, tipo2));

        when(tipoSolicitudMapper.toDto(any(TipoSolicitud.class)))
                .thenReturn(dto1, dto2);

        List<TipoSolicitudDto> resultado = useCase.listarTiposSolicitud();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(tipoSolicitudRepository).findAll();
        verify(tipoSolicitudMapper, times(2)).toDto(any(TipoSolicitud.class));
    }

    @Test
    void deberiaRetornarListaVaciaSiNoHayTiposSolicitud() {

        when(tipoSolicitudRepository.findAll())
                .thenReturn(List.of());

        List<TipoSolicitudDto> resultado = useCase.listarTiposSolicitud();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(tipoSolicitudRepository).findAll();
        verify(tipoSolicitudMapper, never()).toDto(any());
    }
}
