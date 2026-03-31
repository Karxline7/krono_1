package datacenter.mallaturnos.infrastructure.mappers;


import com.datacenter.mallaturnos.application.Dto.AsignacionTurno.AsignacionTurnoDto;
import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.infrastructure.mappers.AsignacionTurnoMapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AsignacionTurnoMapperTest {

    private final AsignacionTurnoMapper mapper = new AsignacionTurnoMapper();

    @Test
    void toDto_correctamente() {
        AsignacionTurno asignacion = new AsignacionTurno();
        asignacion.setId(1L);
        asignacion.setFuncionarioId(10L);
        asignacion.setTurnoId(20L);
        asignacion.setFecha(LocalDate.of(2024, 1, 1));

        AsignacionTurnoDto dto = mapper.toDto(asignacion);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getFuncionarioId());
        assertEquals(20L, dto.getTurnoId());
        assertEquals(LocalDate.of(2024, 1, 1), dto.getFecha());
    }

    @Test
    void toDto_null() {
        AsignacionTurnoDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        AsignacionTurnoDto dto = new AsignacionTurnoDto();
        dto.setId(1L);
        dto.setFuncionarioId(10L);
        dto.setTurnoId(20L);
        dto.setFecha(LocalDate.of(2024, 1, 1));

        AsignacionTurno asignacion = mapper.toDomain(dto);

        assertNotNull(asignacion);
        assertEquals(1L, asignacion.getId());
        assertEquals(10L, asignacion.getFuncionarioId());
        assertEquals(20L, asignacion.getTurnoId());
        assertEquals(LocalDate.of(2024, 1, 1), asignacion.getFecha());
    }

    @Test
    void toDomain_null() {
        AsignacionTurno asignacion = mapper.toDomain(null);
        assertNull(asignacion);
    }
}