package datacenter.mallaturnos.application.UseCase.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.EditarUsuarioDto;
import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import com.datacenter.mallaturnos.application.UseCase.Usuario.EditarUsuarioUseCase;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @InjectMocks
    private EditarUsuarioUseCase editarUsuarioUseCase;

    @Test
    void editarUsuario_correctamente() {
        EditarUsuarioDto dto = new EditarUsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan Editado");
        dto.setTipoDocumento("CC");
        dto.setRolId(2L);
        dto.setCargoId(3L);
        dto.setAreaId(4L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setTipoDocumento("CC");
        usuario.setNumeroDocumento(1234567890L);
        usuario.setRolId(1L);
        usuario.setCargoId(1L);
        usuario.setAreaId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioDto resultado = editarUsuarioUseCase.editarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("Juan Editado", resultado.getNombre());
        assertEquals("CC", resultado.getTipoDocumento());

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void editarUsuario_noExiste() {
        EditarUsuarioDto dto = new EditarUsuarioDto();
        dto.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> editarUsuarioUseCase.editarUsuario(dto)
        );

        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository, never()).save(any());
    }
}
