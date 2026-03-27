package datacenter.mallaturnos.application.UseCase.Rol;

import com.datacenter.mallaturnos.application.UseCase.Rol.EliminarRolUseCase;
import com.datacenter.mallaturnos.domain.model.Rol;
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
class EliminarRolUseCaseTest {

    @Mock
    private RolRepositoryPort rolRepository;

    @InjectMocks
    private EliminarRolUseCase useCase;

    // ❌ Rol no existe
    @Test
    void deberiaLanzarExcepcionSiRolNoExiste() {

        when(rolRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.eliminarRol(1L);
        });

        verify(rolRepository).findById(1L);
        verify(rolRepository, never()).delete(anyLong());
    }

    // ✅ Eliminar rol correctamente
    @Test
    void deberiaEliminarRolCorrectamente() {

        when(rolRepository.findById(1L))
                .thenReturn(Optional.of(new Rol()));

        boolean resultado = useCase.eliminarRol(1L);

        assertTrue(resultado);

        verify(rolRepository).findById(1L);
        verify(rolRepository).delete(1L);
    }
}
