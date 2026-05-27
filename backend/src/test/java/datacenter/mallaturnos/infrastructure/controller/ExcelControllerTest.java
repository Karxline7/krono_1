package datacenter.mallaturnos.infrastructure.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.datacenter.krono_12.infrastructure.controller.Excel.ExcelController;
import com.datacenter.krono_12.infrastructure.port.in.excel.GenerarExcelAsignacionesUseCasePort;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
class ExcelControllerTest {

    @Mock
    private GenerarExcelAsignacionesUseCasePort generarExcelAsignacionesUseCasePort;

    @InjectMocks
    private ExcelController excelController;

    @Test
    void generarExcelAsignaciones_retornaResponseEntityConArchivo() {
        byte[] excelBytes = new byte[]{1, 2, 3};

        when(generarExcelAsignacionesUseCasePort.generarExcelGeneral()).thenReturn(excelBytes);

        ResponseEntity<byte[]> response = excelController.generarExcelAsignaciones();

        assertNotNull(response);
        assertArrayEquals(excelBytes, response.getBody());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertTrue(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0)
                .contains("asignaciones.xlsx"));

        verify(generarExcelAsignacionesUseCasePort, times(1)).generarExcelGeneral();
    }

    @Test
    void generarExcelAsignacionesPorUsuario_retornaResponseEntityConArchivo() {
        Long usuarioId = 1L;
        byte[] excelBytes = new byte[]{4, 5, 6};

        when(generarExcelAsignacionesUseCasePort.generarExcelPorUsuario(usuarioId)).thenReturn(excelBytes);

        ResponseEntity<byte[]> response = excelController.generarExcelAsignacionesPorUsuario(usuarioId);

        assertNotNull(response);
        assertArrayEquals(excelBytes, response.getBody());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertTrue(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0)
                .contains("asignaciones_usuario.xlsx"));

        verify(generarExcelAsignacionesUseCasePort, times(1)).generarExcelPorUsuario(usuarioId);
    }
}
