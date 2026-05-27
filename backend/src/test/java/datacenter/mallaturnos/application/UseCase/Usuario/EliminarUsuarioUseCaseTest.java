package datacenter.mallaturnos.application.UseCase.Usuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.UseCase.Usuario.EliminarUsuarioUseCase;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EliminarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @InjectMocks
    private EliminarUsuarioUseCase eliminarUsuarioUseCase;

    @Test
    void eliminarUsuario_correctamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(1L);

        boolean resultado = eliminarUsuarioUseCase.eliminarUsuario(1L);

        assertTrue(resultado);

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).delete(1L);
    }

    @Test
    void eliminarUsuario_noExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eliminarUsuarioUseCase.eliminarUsuario(1L)
        );

        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository, never()).delete(any());
    }
}
