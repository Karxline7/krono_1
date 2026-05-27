package datacenter.mallaturnos.application.UseCase.Usuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.UseCase.Usuario.ObtenerUsuarioUseCase;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObtenerUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private ObtenerUsuarioUseCase obtenerUsuarioUseCase;

    @Test
    void obtenerUsuario_existe() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(dto);

        Optional<UsuarioDto> resultado = obtenerUsuarioUseCase.obtenerUsuario(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());

        verify(usuarioRepository).findById(1L);
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void obtenerUsuario_noExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UsuarioDto> resultado = obtenerUsuarioUseCase.obtenerUsuario(1L);

        assertFalse(resultado.isPresent());

        verify(usuarioRepository).findById(1L);
        verify(usuarioMapper, never()).toDto(any());
    }

    @Test
    void obtenerPorNumeroDocumento_existe() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");

        when(usuarioRepository.findByNumeroDocumento(123L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(dto);

        Optional<UsuarioDto> resultado = obtenerUsuarioUseCase.obtenerPorNumeroDocumento(123L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());

        verify(usuarioRepository).findByNumeroDocumento(123L);
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void obtenerPorNumeroDocumento_noExiste() {
        when(usuarioRepository.findByNumeroDocumento(123L)).thenReturn(Optional.empty());

        Optional<UsuarioDto> resultado = obtenerUsuarioUseCase.obtenerPorNumeroDocumento(123L);

        assertFalse(resultado.isPresent());

        verify(usuarioRepository).findByNumeroDocumento(123L);
        verify(usuarioMapper, never()).toDto(any());
    }
}
