package com.datacenter.mallaturnos.infrastructure.port.in.Usuario;

import com.datacenter.mallaturnos.application.Dto.Usuario.UsuarioDto;
import java.util.List;

public interface ListarUsuariosUseCasePort {

    List<UsuarioDto> listarPorArea(Long areaId);

    List<UsuarioDto> listarPorCargo(Long cargoId);

    List<UsuarioDto> listarPorAreaYRol(Long areaId, Long rolId);
}
