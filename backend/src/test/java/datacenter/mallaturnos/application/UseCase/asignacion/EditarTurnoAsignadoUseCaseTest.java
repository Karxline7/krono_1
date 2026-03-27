package datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.Request.EditarTurnoAsignadoRequest;
import com.datacenter.mallaturnos.application.UseCase.asignacion.EditarTurnoAsignadoUseCase;
import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.domain.model.Usuario;
import com.datacenter.mallaturnos.domain.model.Turno;
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
class EditarTurnoAsignadoUseCaseTest {

    @Mock
    private AsignacionRepositoryPort asignacionRepository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private TurnoRepositoryPort turnoRepository;

    @Mock
    private AsignacionTurnoMapper mapper;

    @InjectMocks
    private EditarTurnoAsignadoUseCase useCase;

    // ✅ 1. Caso exitoso
    @Test
    void deberiaEditarAsignacionCorrectamente() {

        Long id = 1L;

        EditarTurnoAsignadoRequest request = new EditarTurnoAsignadoRequest();
        request.setNuevoFuncionarioId(2L);
        request.setNuevoTurnoId(3L);
        request.setNuevaFecha(LocalDate.now());

        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setId(id);

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(new Usuario()));

        when(turnoRepository.findById(3L))
                .thenReturn(Optional.of(new Turno()));

        when(asignacionRepository.findByFuncionarioAndFecha(2L, request.getNuevaFecha()))
                .thenReturn(Optional.empty());

        when(asignacionRepository.save(asignacion))
                .thenReturn(asignacion);

        when(mapper.toDto(asignacion))
                .thenReturn(new AsignacionTurnoDto());

        AsignacionTurnoDto resultado = useCase.editarTurnoAsignado(id, request);

        assertNotNull(resultado);
        verify(asignacionRepository).save(asignacion);
    }

    // ❌ 2. Asignación no existe
    @Test
    void deberiaFallarSiAsignacionNoExiste() {

        when(asignacionRepository.findById(1L))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.editarTurnoAsignado(1L, new EditarTurnoAsignadoRequest()));

        assertEquals("Asignación no encontrada", ex.getMessage());
    }

    // ❌ 3. Funcionario no existe
    @Test
    void deberiaFallarSiFuncionarioNoExiste() {

        Long id = 1L;

        EditarTurnoAsignadoRequest request = new EditarTurnoAsignadoRequest();
        request.setNuevoFuncionarioId(2L);

        AsignacionTurno asignacion = new AsignacionTurno();

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.editarTurnoAsignado(id, request));

        assertEquals("Funcionario no encontrado", ex.getMessage());
    }

    // ❌ 4. Turno no existe
    @Test
    void deberiaFallarSiTurnoNoExiste() {

        Long id = 1L;

        EditarTurnoAsignadoRequest request = new EditarTurnoAsignadoRequest();
        request.setNuevoFuncionarioId(2L);
        request.setNuevoTurnoId(3L);

        AsignacionTurno asignacion = new AsignacionTurno();

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(new Usuario()));

        when(turnoRepository.findById(3L))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.editarTurnoAsignado(id, request));

        assertEquals("Turno no encontrado", ex.getMessage());
    }

    // ❌ 5. Conflicto de fecha
    @Test
    void deberiaFallarSiYaExisteAsignacionEnFecha() {

        Long id = 1L;

        EditarTurnoAsignadoRequest request = new EditarTurnoAsignadoRequest();
        request.setNuevoFuncionarioId(2L);
        request.setNuevaFecha(LocalDate.now());

        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setId(id);

        AsignacionTurno conflicto = new AsignacionTurno();
        conflicto.setId(99L); // distinto ID → conflicto real

        when(asignacionRepository.findById(id))
                .thenReturn(Optional.of(asignacion));

        when(usuarioRepository.findById(2L))
                .thenReturn(Optional.of(new Usuario()));

        when(asignacionRepository.findByFuncionarioAndFecha(2L, request.getNuevaFecha()))
                .thenReturn(Optional.of(conflicto));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.editarTurnoAsignado(id, request));

        assertEquals("El funcionario ya tiene asignación en esa fecha", ex.getMessage());
    }
}
