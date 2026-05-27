package com.datacenter.krono_12.infrastructure.port.in.Usuario;

import java.util.List;

import com.datacenter.krono_12.application.Dto.Usuario.UsuarioDto;

public interface ListarUsuariosUseCasePort {

    List<UsuarioDto> listar();

    List<UsuarioDto> listarPorArea(Long areaId);

    List<UsuarioDto> listarPorCargo(Long cargoId);

    List<UsuarioDto> listarPorAreaYRol(Long areaId, Long rolId);
}
