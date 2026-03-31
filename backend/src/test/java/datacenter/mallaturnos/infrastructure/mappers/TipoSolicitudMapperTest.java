package datacenter.mallaturnos.infrastructure.mappers;

import com.datacenter.mallaturnos.application.Dto.TipoSolicitud.TipoSolicitudDto;
import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.mappers.TipoSolicitudMapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoSolicitudMapperTest {

    private final TipoSolicitudMapper mapper = new TipoSolicitudMapper();

    @Test
    void toDto_correctamente() {
        TipoSolicitud tipo = new TipoSolicitud();
        tipo.setId(1L);
        tipo.setNombre("Cambio de turno");
        tipo.setDescripcion("Solicitud para cambiar turno");

        TipoSolicitudDto dto = mapper.toDto(tipo);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Cambio de turno", dto.getNombre());
        assertEquals("Solicitud para cambiar turno", dto.getDescripcion());
    }

    @Test
    void toDto_null() {
        TipoSolicitudDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        TipoSolicitudDto dto = new TipoSolicitudDto();
        dto.setId(2L);
        dto.setNombre("Permiso");
        dto.setDescripcion("Solicitud de permiso");

        TipoSolicitud tipo = mapper.toDomain(dto);

        assertNotNull(tipo);
        assertEquals(2L, tipo.getId());
        assertEquals("Permiso", tipo.getNombre());
        assertEquals("Solicitud de permiso", tipo.getDescripcion());
    }

    @Test
    void toDomain_null() {
        TipoSolicitud tipo = mapper.toDomain(null);
        assertNull(tipo);
    }
}
