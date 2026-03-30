package datacenter.mallaturnos.application.UseCase.tiposolicitud;

import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.application.UseCase.tiposolicitud.EditarTipoSolicitudUseCase;
import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditarTipoSolicitudUseCaseTest {

    @Mock
    private TipoSolicitudRepositoryPort tipoSolicitudRepository;

    @Mock
    private TipoSolicitudMapper tipoSolicitudMapper;

    @InjectMocks
    private EditarTipoSolicitudUseCase useCase;

    @Test
    void deberiaEditarTipoSolicitudCorrectamente() {

        Long id = 1L;

        TipoSolicitudDto dto = new TipoSolicitudDto();
        dto.setNombre("Nuevo Nombre");
        dto.setDescripcion("Nueva Descripcion");

        TipoSolicitud tipoExistente = new TipoSolicitud();
        tipoExistente.setId(id);
        tipoExistente.setNombre("Viejo");
        tipoExistente.setDescripcion("Vieja");

        TipoSolicitud tipoGuardado = new TipoSolicitud();
        TipoSolicitudDto dtoRespuesta = new TipoSolicitudDto();

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.of(tipoExistente));

        when(tipoSolicitudRepository.save(tipoExistente))
                .thenReturn(tipoGuardado);

        when(tipoSolicitudMapper.toDto(tipoGuardado))
                .thenReturn(dtoRespuesta);

        TipoSolicitudDto resultado = useCase.editarTipoSolicitud(id, dto);

        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", tipoExistente.getNombre());
        assertEquals("Nueva Descripcion", tipoExistente.getDescripcion());

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudRepository).save(tipoExistente);
        verify(tipoSolicitudMapper).toDto(tipoGuardado);
    }

    @Test
    void deberiaLanzarExcepcionSiTipoSolicitudNoExiste() {

        Long id = 1L;
        TipoSolicitudDto dto = new TipoSolicitudDto();

        when(tipoSolicitudRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.editarTipoSolicitud(id, dto);
        });

        verify(tipoSolicitudRepository).findById(id);
        verify(tipoSolicitudRepository, never()).save(any());
    }
}
