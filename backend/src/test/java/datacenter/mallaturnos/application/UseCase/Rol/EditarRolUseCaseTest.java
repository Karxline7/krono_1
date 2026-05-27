package datacenter.mallaturnos.application.UseCase.Rol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.application.UseCase.Rol.EditarRolUseCase;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditarRolUseCaseTest {

    @Mock
    private RolRepositoryPort rolRepository;

    @Mock
    private RolMapper rolMapper;

    @InjectMocks
    private EditarRolUseCase useCase;

    // ❌ Rol no existe
    @Test
    void deberiaLanzarExcepcionSiRolNoExiste() {

        RolDto dto = new RolDto();
        dto.setNombre("ADMIN_EDITADO");
        dto.setDescripcion("Descripcion editada");

        when(rolRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.editarRol(1L, dto);
        });

        verify(rolRepository, never()).save(any());
        verify(rolMapper, never()).toDto(any());
    }

    // ✅ Editar rol correctamente
    @Test
    void deberiaEditarRolCorrectamente() {

        RolDto dto = new RolDto();
        dto.setNombre("ADMIN_EDITADO");
        dto.setDescripcion("Descripcion editada");

        Rol rolExistente = new Rol();
        Rol rolGuardado = new Rol();
        RolDto dtoRespuesta = new RolDto();

        when(rolRepository.findById(1L))
                .thenReturn(Optional.of(rolExistente));

        when(rolRepository.save(rolExistente))
                .thenReturn(rolGuardado);

        when(rolMapper.toDto(rolGuardado))
                .thenReturn(dtoRespuesta);

        RolDto resultado = useCase.editarRol(1L, dto);

        assertNotNull(resultado);

        verify(rolRepository).findById(1L);
        verify(rolRepository).save(rolExistente);
        verify(rolMapper).toDto(rolGuardado);
    }
}
