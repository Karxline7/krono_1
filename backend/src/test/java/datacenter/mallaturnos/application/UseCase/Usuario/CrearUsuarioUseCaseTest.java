package datacenter.mallaturnos.application.UseCase.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Usuario.CrearUsuarioDto;
import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.UseCase.Usuario.CrearUsuarioUseCase;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrearUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private CrearUsuarioUseCase crearUsuarioUseCase;

    private CrearUsuarioDto crearUsuarioDto;
    private Usuario usuario;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        crearUsuarioDto = new CrearUsuarioDto();
        crearUsuarioDto.setNombre("Juan");
        crearUsuarioDto.setTipoDocumento("CC");
        crearUsuarioDto.setNumeroDocumento(1234567890L);
        crearUsuarioDto.setContrasena(123456);
        crearUsuarioDto.setRolId(1L);
        crearUsuarioDto.setCargoId(1L);
        crearUsuarioDto.setAreaId(1L);

        usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setNumeroDocumento(1234567890L);

        usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Juan");
        usuarioDto.setNumeroDocumento(1234567890L);
    }

    @Test
    void crearUsuario_correctamente() {
        when(usuarioRepository.existsByNumeroDocumento(crearUsuarioDto.getNumeroDocumento())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDto);

        UsuarioDto resultado = crearUsuarioUseCase.crearUsuario(crearUsuarioDto);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());

        verify(usuarioRepository).existsByNumeroDocumento(crearUsuarioDto.getNumeroDocumento());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioMapper).toDto(usuario);
    }

    @Test
    void crearUsuario_yaExiste() {
        when(usuarioRepository.existsByNumeroDocumento(crearUsuarioDto.getNumeroDocumento())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> crearUsuarioUseCase.crearUsuario(crearUsuarioDto)
        );

        assertEquals("El número de documento ya existe", exception.getMessage());

        verify(usuarioRepository).existsByNumeroDocumento(crearUsuarioDto.getNumeroDocumento());
        verify(usuarioRepository, never()).save(any());
    }
}