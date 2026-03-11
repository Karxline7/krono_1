package com.datacenter.mallaturnos.port.in.Usuario;

import com.datacenter.mallaturnos.domain.model.Usuario;
import java.util.List;

public interface ListarUsuariosUseCasePort {

    List<Usuario> listarPorArea(Long areaId);

    List<Usuario> listarPorCargo(Long cargoId);

    List<Usuario> listarPorAreaYRol(Long areaId, Long rolId);
}
