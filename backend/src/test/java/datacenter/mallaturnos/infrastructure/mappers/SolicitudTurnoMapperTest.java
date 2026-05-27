package datacenter.mallaturnos.infrastructure.mappers;

import org.junit.jupiter.api.Test;

import com.datacenter.krono_12.application.Dto.SolicitudTurno.SolicitudTurnoDto;
import com.datacenter.krono_12.domain.model.EstadoSolicitud;
import com.datacenter.krono_12.domain.model.SolicitudTurno;
import com.datacenter.krono_12.infrastructure.mappers.SolicitudTurnoMapper;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudTurnoMapperTest {

    private final SolicitudTurnoMapper mapper = new SolicitudTurnoMapper();

    @Test
    void toDto_correctamente() {
        SolicitudTurno solicitud = new SolicitudTurno();
        solicitud.setId(1L);
        solicitud.setAsignacionTurnoId(10L);
        solicitud.setTipoSolicitudId(20L);
        solicitud.setMotivoSolicitud("Cambio de turno");
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        SolicitudTurnoDto dto = mapper.toDto(solicitud);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getAsignacionTurnoId());
        assertEquals(20L, dto.getTipoSolicitudId());
        assertEquals("Cambio de turno", dto.getMotivoSolicitud());
        assertEquals(EstadoSolicitud.PENDIENTE, dto.getEstado());
    }

    @Test
    void toDto_null() {
        SolicitudTurnoDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        SolicitudTurnoDto dto = new SolicitudTurnoDto();
        dto.setId(1L);
        dto.setAsignacionTurnoId(10L);
        dto.setTipoSolicitudId(20L);
        dto.setMotivoSolicitud("Cambio de turno");
        dto.setEstado(EstadoSolicitud.APROBADA);

        SolicitudTurno solicitud = mapper.toDomain(dto);

        assertNotNull(solicitud);
        assertEquals(1L, solicitud.getId());
        assertEquals(10L, solicitud.getAsignacionTurnoId());
        assertEquals(20L, solicitud.getTipoSolicitudId());
        assertEquals("Cambio de turno", solicitud.getMotivoSolicitud());
        assertEquals(EstadoSolicitud.APROBADA, solicitud.getEstado());
    }

    @Test
    void toDomain_null() {
        SolicitudTurno solicitud = mapper.toDomain(null);
        assertNull(solicitud);
    }
}