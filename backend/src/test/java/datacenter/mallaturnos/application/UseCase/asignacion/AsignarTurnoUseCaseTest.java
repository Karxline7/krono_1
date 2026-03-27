package datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.UseCase.asignacion.AsignarTurnoUseCase;
import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.domain.model.Turno;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignarTurnoUseCaseTest {

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private AsignacionTurnoMapper mapper;

    @InjectMocks
    private AsignarTurnoUseCase useCase;

    // ✅ 1. Caso exitoso
    @Test
    void deberiaAsignarTurnoCorrectamente() {

        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setFuncionarioId(1L);
        dto.setTurnoId(7L);
        dto.setFecha(LocalDate.now());

        AsignacionTurno domain = new AsignacionTurno();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(turnoRepository.findById(7L)).thenReturn(Optional.of(new Turno()));
        when(asignacionRepository.existsByFuncionarioAndFecha(1L, dto.getFecha())).thenReturn(false);
        when(mapper.toDomain(dto)).thenReturn(domain);
        when(asignacionRepository.save(domain)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(dto);

        AsignacionTurnoDto resultado = useCase.asignarTurno(dto);

        assertNotNull(resultado);
        verify(asignacionRepository).save(domain);
    }

    // ❌ 2. Funcionario no existe
    @Test
    void deberiaLanzarErrorSiFuncionarioNoExiste() {

        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setFuncionarioId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.asignarTurno(dto));

        assertEquals("Funcionario no encontrado", ex.getMessage());
    }

    // ❌ 3. Turno no existe
    @Test
    void deberiaLanzarErrorSiTurnoNoExiste() {

        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setFuncionarioId(1L);
        dto.setTurnoId(7L);
        dto.setFecha(LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(turnoRepository.findById(7L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.asignarTurno(dto));

        assertEquals("Turno no encontrado", ex.getMessage());
    }

    // ❌ 4. Ya existe asignación
    @Test
    void deberiaLanzarErrorSiYaExisteAsignacion() {

        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setFuncionarioId(1L);
        dto.setTurnoId(7L);
        dto.setFecha(LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));
        when(turnoRepository.findById(7L)).thenReturn(Optional.of(new Turno()));
        when(asignacionRepository.existsByFuncionarioAndFecha(1L, dto.getFecha())).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.asignarTurno(dto));

        assertEquals("El funcionario ya tiene asignación en esa fecha", ex.getMessage());
    }
}
