package datacenter.mallaturnos.infrastructure.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;
import com.datacenter.krono_12.domain.model.Usuario;
import com.datacenter.krono_12.infrastructure.adapter.out.persistence.repository.CargoJpaRepository;
import com.datacenter.krono_12.infrastructure.mappers.UsuarioMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioMapperTest {

    @Mock
    private CargoJpaRepository cargoRepository;

    private UsuarioMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new UsuarioMapper(cargoRepository);
    }

    @Test
    void toDto_correctamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setTipoDocumento("CC");
        usuario.setNumeroDocumento(1234567890L);
        usuario.setRolId(2L);
        usuario.setCargoId(3L);
        usuario.setAreaId(4L);

        when(cargoRepository.findNombreById(3L)).thenReturn("Analista");

        UsuarioDto dto = mapper.toDto(usuario);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Juan", dto.getNombre());
        assertEquals("CC", dto.getTipoDocumento());
        assertEquals(1234567890L, dto.getNumeroDocumento());
        assertEquals(2L, dto.getRolId());
        assertEquals(3L, dto.getCargoId());
        assertEquals("Analista", dto.getCargoNombre());
        assertEquals(4L, dto.getAreaId());

        verify(cargoRepository).findNombreById(3L);
    }

    @Test
    void toDto_null() {
        UsuarioDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    void toDomain_correctamente() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setTipoDocumento("CC");
        dto.setNumeroDocumento(1234567890L);
        dto.setRolId(2L);
        dto.setCargoId(3L);
        dto.setAreaId(4L);

        Usuario usuario = mapper.toDomain(dto);

        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("CC", usuario.getTipoDocumento());
        assertEquals(1234567890L, usuario.getNumeroDocumento());
        assertEquals(2L, usuario.getRolId());
        assertEquals(3L, usuario.getCargoId());
        assertEquals(4L, usuario.getAreaId());
    }

    @Test
    void toDomain_null() {
        Usuario usuario = mapper.toDomain(null);
        assertNull(usuario);
    }
}
