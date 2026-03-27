package datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.application.UseCase.Rol.ObtenerRolUseCase;
import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObtenerRolUseCaseTest {

    @Mock
    private RolRepositoryPort rolRepository;

    @Mock
    private RolMapper rolMapper;

    @InjectMocks
    private ObtenerRolUseCase useCase;

    // ✅ Obtener rol correctamente
    @Test
    void deberiaObtenerRolCorrectamente() {

        Rol rol = new Rol();
        RolDto dto = new RolDto();

        when(rolRepository.findById(1L))
                .thenReturn(Optional.of(rol));

        when(rolMapper.toDto(rol))
                .thenReturn(dto);

        Optional<RolDto> resultado = useCase.obtenerRol(1L);

        assertTrue(resultado.isPresent());

        verify(rolRepository).findById(1L);
        verify(rolMapper).toDto(rol);
    }

    // ✅ Rol no existe
    @Test
    void deberiaRetornarOptionalVacioSiRolNoExiste() {

        when(rolRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<RolDto> resultado = useCase.obtenerRol(1L);

        assertFalse(resultado.isPresent());

        verify(rolRepository).findById(1L);
        verify(rolMapper, never()).toDto(any());
    }
}
