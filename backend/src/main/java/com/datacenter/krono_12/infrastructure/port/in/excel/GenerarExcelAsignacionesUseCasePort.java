package com.datacenter.krono_12.infrastructure.port.in.excel;

public interface GenerarExcelAsignacionesUseCasePort {
    
    byte[] generarExcelGeneral();

    byte[] generarExcelPorUsuario(Long usuarioId);

    byte[] generarExcelPorArea(Long areaId);
}
