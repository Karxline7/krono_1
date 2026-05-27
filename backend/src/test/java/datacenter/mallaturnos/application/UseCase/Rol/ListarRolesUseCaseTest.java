package datacenter.mallaturnos.application.UseCase.Rol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.application.UseCase.Rol.ListarRolesUseCase;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarRolesUseCaseTest {

    @Mock
    private RolRepositoryPort rolRepository;

    @Mock
    private RolMapper rolMapper;

    @InjectMocks
    private ListarRolesUseCase useCase;

    // ✅ Listar roles correctamente
    @Test
    void deberiaListarRolesCorrectamente() {

        Rol rol1 = new Rol();
        Rol rol2 = new Rol();

        RolDto dto1 = new RolDto();
        RolDto dto2 = new RolDto();

        when(rolRepository.findAll())
                .thenReturn(List.of(rol1, rol2));

        when(rolMapper.toDto(rol1)).thenReturn(dto1);
        when(rolMapper.toDto(rol2)).thenReturn(dto2);

        List<RolDto> resultado = useCase.listarRoles();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(rolMapper, times(2)).toDto(any());
    }

    // ✅ Lista vacía
    @Test
    void deberiaRetornarListaVaciaSiNoHayRoles() {

        when(rolRepository.findAll())
                .thenReturn(List.of());

        List<RolDto> resultado = useCase.listarRoles();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(rolRepository).findAll();
        verify(rolMapper, never()).toDto(any());
    }
}
