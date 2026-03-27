package datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.application.Dto.Rol.RolDto;
import com.datacenter.mallaturnos.application.UseCase.Rol.CrearRolUseCase;
import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.mappers.RolMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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