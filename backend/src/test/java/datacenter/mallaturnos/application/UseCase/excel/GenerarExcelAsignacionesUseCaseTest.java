package datacenter.mallaturnos.application.UseCase.excel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.application.UseCase.excel.GenerarExcelAsignacionesUseCase;
import com.datacenter.mallaturnos.infrastructure.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.UsuarioRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.ExcelGeneratorPort;

@ExtendWith(MockitoExtension.class)
class GenerarExcelAsignacionesUseCaseTest {

    @Mock
    private ExcelGeneratorPort excelGeneratorPort;

    @Mock
    private AsignacionRepositoryPort asignacionTurnoRepositoryPort;

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private TurnoRepositoryPort turnoRepositoryPort;

    @InjectMocks
    private GenerarExcelAsignacionesUseCase useCase;

    @Test
    void generarExcelGeneral_listaNoVacia_retornaBytes() {
        List<AsignacionTurnoDto> asignaciones = new ArrayList<>();
        asignaciones.add(new AsignacionTurnoDto());

        when(asignacionTurnoRepositoryPort.listarAsignaciones()).thenReturn(asignaciones);
        when(usuarioRepositoryPort.findAllWithCargo()).thenReturn(new ArrayList<>());
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap())).thenReturn(new byte[]{1,2,3});

        byte[] resultado = useCase.generarExcelGeneral();

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{1,2,3}, resultado);
        verify(asignacionTurnoRepositoryPort, times(1)).listarAsignaciones();
        verify(excelGeneratorPort, times(1)).generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap());
    }

    @Test
    void generarExcelGeneral_listaVacia_retornaBytes() {
        List<AsignacionTurnoDto> asignaciones = new ArrayList<>();

        when(asignacionTurnoRepositoryPort.listarAsignaciones()).thenReturn(asignaciones);
        when(usuarioRepositoryPort.findAllWithCargo()).thenReturn(new ArrayList<>());
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap())).thenReturn(new byte[]{});

        byte[] resultado = useCase.generarExcelGeneral();

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{}, resultado);
    }

    @Test
    void generarExcelPorUsuario_usuarioExiste_retornaBytes() {
        Long usuarioId = 1L;
        List<AsignacionTurnoDto> asignaciones = List.of(new AsignacionTurnoDto());
        String nombreUsuario = "Juan";

        when(asignacionTurnoRepositoryPort.listarAsignacionesPorUsuario(usuarioId)).thenReturn(asignaciones);
        when(usuarioRepositoryPort.obtenerNombrePorId(usuarioId)).thenReturn(nombreUsuario);
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignacionesPorUsuario(eq(nombreUsuario), eq(asignaciones), anyMap())).thenReturn(new byte[]{1,2,3});

        byte[] resultado = useCase.generarExcelPorUsuario(usuarioId);

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{1,2,3}, resultado);
        verify(excelGeneratorPort, times(1)).generarExcelAsignacionesPorUsuario(eq(nombreUsuario), eq(asignaciones), anyMap());
    }

    @Test
    void generarExcelPorUsuario_usuarioNoTieneAsignaciones_retornaBytesVacios() {
        Long usuarioId = 2L;
        List<AsignacionTurnoDto> asignaciones = new ArrayList<>();
        String nombreUsuario = "Pedro";

        when(asignacionTurnoRepositoryPort.listarAsignacionesPorUsuario(usuarioId)).thenReturn(asignaciones);
        when(usuarioRepositoryPort.obtenerNombrePorId(usuarioId)).thenReturn(nombreUsuario);
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignacionesPorUsuario(eq(nombreUsuario), eq(asignaciones), anyMap())).thenReturn(new byte[]{});

        byte[] resultado = useCase.generarExcelPorUsuario(usuarioId);

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{}, resultado);
    }

    @Test
    void generarExcelPorArea_listaNoVacia_retornaBytes() {
        Long areaId = 1L;
        List<AsignacionTurnoDto> asignaciones = List.of(new AsignacionTurnoDto());

        when(asignacionTurnoRepositoryPort.listarAsignacionesPorArea(areaId)).thenReturn(asignaciones);
        when(usuarioRepositoryPort.findByAreaId(areaId)).thenReturn(new ArrayList<>());
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap())).thenReturn(new byte[]{1,2,3});

        byte[] resultado = useCase.generarExcelPorArea(areaId);

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{1,2,3}, resultado);
        verify(asignacionTurnoRepositoryPort, times(1)).listarAsignacionesPorArea(areaId);
        verify(excelGeneratorPort, times(1)).generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap());
    }

    @Test
    void generarExcelPorArea_listaVacia_retornaBytesVacios() {
        Long areaId = 2L;
        List<AsignacionTurnoDto> asignaciones = new ArrayList<>();

        when(asignacionTurnoRepositoryPort.listarAsignacionesPorArea(areaId)).thenReturn(asignaciones);
        when(usuarioRepositoryPort.findByAreaId(areaId)).thenReturn(new ArrayList<>());
        when(turnoRepositoryPort.findAll()).thenReturn(new ArrayList<>());
        
        when(excelGeneratorPort.generarExcelAsignaciones(eq(asignaciones), anyMap(), anyMap())).thenReturn(new byte[]{});

        byte[] resultado = useCase.generarExcelPorArea(areaId);

        assertNotNull(resultado);
        assertArrayEquals(new byte[]{}, resultado);
    }
}
