package datacenter.mallaturnos.application.UseCase.Usuario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.UseCase.Usuario.ListarUsuariosUseCase;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarUsuariosUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private ListarUsuariosUseCase listarUsuariosUseCase;

    @Test
    void listar_lista() {
        UsuarioDto dto1 = new UsuarioDto();
        UsuarioDto dto2 = new UsuarioDto();

        when(usuarioRepository.findAllWithCargo()).thenReturn(Arrays.asList(dto1, dto2));

        List<UsuarioDto> resultado = listarUsuariosUseCase.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(usuarioRepository).findAllWithCargo();
        verify(usuarioMapper, never()).toDto(any());
    }

    @Test
    void listar_listaVacia() {
        when(usuarioRepository.findAllWithCargo()).thenReturn(Collections.emptyList());

        List<UsuarioDto> resultado = listarUsuariosUseCase.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(usuarioRepository).findAllWithCargo();
    }

    @Test
    void listarPorArea_lista() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioDto dto1 = new UsuarioDto();
        UsuarioDto dto2 = new UsuarioDto();

        when(usuarioRepository.findByAreaId(1L)).thenReturn(Arrays.asList(usuario1, usuario2));
        when(usuarioMapper.toDto(usuario1)).thenReturn(dto1);
        when(usuarioMapper.toDto(usuario2)).thenReturn(dto2);

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorArea(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(usuarioRepository).findByAreaId(1L);
        verify(usuarioMapper, times(2)).toDto(any());
    }

    @Test
    void listarPorArea_listaVacia() {
        when(usuarioRepository.findByAreaId(1L)).thenReturn(Collections.emptyList());

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorArea(1L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(usuarioRepository).findByAreaId(1L);
        verify(usuarioMapper, never()).toDto(any());
    }

    @Test
    void listarPorCargo_lista() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioDto dto1 = new UsuarioDto();
        UsuarioDto dto2 = new UsuarioDto();

        when(usuarioRepository.findByCargoId(1L)).thenReturn(Arrays.asList(usuario1, usuario2));
        when(usuarioMapper.toDto(usuario1)).thenReturn(dto1);
        when(usuarioMapper.toDto(usuario2)).thenReturn(dto2);

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorCargo(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(usuarioRepository).findByCargoId(1L);
        verify(usuarioMapper, times(2)).toDto(any());
    }

    @Test
    void listarPorCargo_listaVacia() {
        when(usuarioRepository.findByCargoId(1L)).thenReturn(Collections.emptyList());

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorCargo(1L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(usuarioRepository).findByCargoId(1L);
        verify(usuarioMapper, never()).toDto(any());
    }

    @Test
    void listarPorAreaYRol_lista() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioDto dto1 = new UsuarioDto();
        UsuarioDto dto2 = new UsuarioDto();

        when(usuarioRepository.findByAreaIdAndRolId(1L, 2L)).thenReturn(Arrays.asList(usuario1, usuario2));
        when(usuarioMapper.toDto(usuario1)).thenReturn(dto1);
        when(usuarioMapper.toDto(usuario2)).thenReturn(dto2);

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorAreaYRol(1L, 2L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(usuarioRepository).findByAreaIdAndRolId(1L, 2L);
        verify(usuarioMapper, times(2)).toDto(any());
    }

    @Test
    void listarPorAreaYRol_listaVacia() {
        when(usuarioRepository.findByAreaIdAndRolId(1L, 2L)).thenReturn(Collections.emptyList());

        List<UsuarioDto> resultado = listarUsuariosUseCase.listarPorAreaYRol(1L, 2L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(usuarioRepository).findByAreaIdAndRolId(1L, 2L);
        verify(usuarioMapper, never()).toDto(any());
    }
}