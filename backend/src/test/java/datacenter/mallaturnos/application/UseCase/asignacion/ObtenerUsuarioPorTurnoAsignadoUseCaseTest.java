package datacenter.mallaturnos.application.UseCase.asignacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.application.UseCase.asignacion.ObtenerUsuarioPorTurnoAsignadoUseCase;
import com.datacenter.krono_12.domain.model.AsignacionTurno;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;
import com.datacenter.krono_12.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.krono_12.infrastructure.port.out.UsuarioRepositoryPort;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObtenerUsuarioPorTurnoAsignadoUseCaseTest {

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private ObtenerUsuarioPorTurnoAsignadoUseCase useCase;

    // ❌ No existe asignación
    @Test
    void deberiaRetornarVacioSiNoHayAsignacion() {

        LocalDate fecha = LocalDate.now();

        when(asignacionRepository.findByFuncionarioAndFecha(1L, fecha))
                .thenReturn(Optional.empty());

        Optional<UsuarioDto> resultado =
                useCase.obtenerUsuarioPorTurnoAsignado(1L, fecha);

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, never()).findById(any());
    }

    // ❌ Existe asignación pero no usuario
    @Test
    void deberiaRetornarVacioSiUsuarioNoExiste() {

        LocalDate fecha = LocalDate.now();

        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setFuncionarioId(1L);

        when(asignacionRepository.findByFuncionarioAndFecha(1L, fecha))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<UsuarioDto> resultado =
                useCase.obtenerUsuarioPorTurnoAsignado(1L, fecha);

        assertTrue(resultado.isEmpty());
    }

    // ✅ Caso exitoso
    @Test
    void deberiaRetornarUsuarioDto() {

        LocalDate fecha = LocalDate.now();

        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setFuncionarioId(1L);

        Usuario usuario = new Usuario();
        UsuarioDto dto = new UsuarioDto();

        when(asignacionRepository.findByFuncionarioAndFecha(1L, fecha))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toDto(usuario))
                .thenReturn(dto);

        Optional<UsuarioDto> resultado =
                useCase.obtenerUsuarioPorTurnoAsignado(1L, fecha);

        assertTrue(resultado.isPresent());
        verify(usuarioMapper).toDto(usuario);
    }
}