package datacenter.mallaturnos.application.UseCase.Rol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.application.UseCase.Rol.CrearRolUseCase;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;
import com.datacenter.krono_12.infrastructure.port.out.RolRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearRolUseCaseTest {

    @Mock
    private RolRepositoryPort rolRepository;

    @Mock
    private RolMapper rolMapper;

    @InjectMocks
    private CrearRolUseCase useCase;

    // ❌ Ya existe el rol
    @Test
    void deberiaLanzarExcepcionSiRolYaExiste() {

        RolDto dto = new RolDto();
        dto.setNombre("ADMIN");

        when(rolRepository.findByNombre("ADMIN"))
                .thenReturn(Optional.of(new Rol()));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.crearRol(dto);
        });

        verify(rolRepository, never()).save(any());
    }

    // ✅ Crear rol correctamente
    @Test
    void deberiaCrearRolCorrectamente() {

        RolDto dto = new RolDto();
        dto.setNombre("ADMIN");

        Rol rolDomain = new Rol();
        Rol rolGuardado = new Rol();
        RolDto dtoRespuesta = new RolDto();

        when(rolRepository.findByNombre("ADMIN"))
                .thenReturn(Optional.empty());

        when(rolMapper.toDomain(dto))
                .thenReturn(rolDomain);

        when(rolRepository.save(rolDomain))
                .thenReturn(rolGuardado);

        when(rolMapper.toDto(rolGuardado))
                .thenReturn(dtoRespuesta);

        RolDto resultado = useCase.crearRol(dto);

        assertNotNull(resultado);

        verify(rolRepository).save(rolDomain);
        verify(rolMapper).toDto(rolGuardado);
    }
}