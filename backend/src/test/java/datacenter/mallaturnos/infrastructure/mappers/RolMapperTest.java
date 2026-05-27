package datacenter.mallaturnos.infrastructure.mappers;

import org.junit.jupiter.api.Test;

import com.datacenter.krono_12.application.Dto.Rol.RolDto;
import com.datacenter.krono_12.domain.model.Rol;
import com.datacenter.krono_12.infrastructure.mappers.RolMapper;

import static org.junit.jupiter.api.Assertions.*;

public class RolMapperTest {

    private final RolMapper mapper = new RolMapper();

    @Test
    void toDto_correctamente() {
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador del sistema");

        RolDto dto = mapper.toDto(rol);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("ADMIN", dto.getNombre());
        assertEquals("Administrador del sistema", dto.getDescripcion());
    }

    @Test
    void toDto_null() {
        RolDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        RolDto dto = new RolDto();
        dto.setId(1L);
        dto.setNombre("USER");
        dto.setDescripcion("Usuario del sistema");

        Rol rol = mapper.toDomain(dto);

        assertNotNull(rol);
        assertEquals(1L, rol.getId());
        assertEquals("USER", rol.getNombre());
        assertEquals("Usuario del sistema", rol.getDescripcion());
    }

    @Test
    void toDomain_null() {
        Rol rol = mapper.toDomain(null);
        assertNull(rol);
    }
}
