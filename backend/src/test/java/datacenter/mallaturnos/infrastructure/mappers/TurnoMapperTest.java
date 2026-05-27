package datacenter.mallaturnos.infrastructure.mappers;

import org.junit.jupiter.api.Test;

import com.datacenter.krono_12.application.Dto.Turno.TurnoDto;
import com.datacenter.krono_12.domain.model.Turno;
import com.datacenter.krono_12.infrastructure.mappers.TurnoMapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

public class TurnoMapperTest {

    private final TurnoMapper mapper = new TurnoMapper();

    @Test
    void toDto_correctamente() {
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setNombre("Turno Mañana");
        turno.setHoraInicio(LocalTime.of(8, 0));
        turno.setHoraFin(LocalTime.of(17, 0));
        turno.setHoraalmuerzo(LocalTime.of(12, 0));
        turno.setHorabreak(LocalTime.of(15, 0));

        TurnoDto dto = mapper.toDto(turno);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Turno Mañana", dto.getNombre());
        assertEquals(LocalTime.of(8, 0), dto.getHoraInicio());
        assertEquals(LocalTime.of(17, 0), dto.getHoraFin());
        assertEquals(LocalTime.of(12, 0), dto.getHoraalmuerzo());
        assertEquals(LocalTime.of(15, 0), dto.getHorabreak());
    }

    @Test
    void toDto_null() {
        TurnoDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        TurnoDto dto = new TurnoDto();
        dto.setId(2L);
        dto.setNombre("Turno Tarde");
        dto.setHoraInicio(LocalTime.of(13, 0));
        dto.setHoraFin(LocalTime.of(21, 0));
        dto.setHoraalmuerzo(LocalTime.of(17, 0));
        dto.setHorabreak(LocalTime.of(19, 0));

        Turno turno = mapper.toDomain(dto);

        assertNotNull(turno);
        assertEquals(2L, turno.getId());
        assertEquals("Turno Tarde", turno.getNombre());
        assertEquals(LocalTime.of(13, 0), turno.getHoraInicio());
        assertEquals(LocalTime.of(21, 0), turno.getHoraFin());
        assertEquals(LocalTime.of(17, 0), turno.getHoraalmuerzo());
        assertEquals(LocalTime.of(19, 0), turno.getHorabreak());
    }

    @Test
    void toDomain_null() {
        Turno turno = mapper.toDomain(null);
        assertNull(turno);
    }
}
