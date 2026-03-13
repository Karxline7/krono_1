package com.datacenter.mallaturnos.infrastructure.config;

import com.datacenter.mallaturnos.domain.model.Rol;
import com.datacenter.mallaturnos.domain.model.TipoSolicitud;
import com.datacenter.mallaturnos.infrastructure.port.out.RolRepositoryPort;
import com.datacenter.mallaturnos.infrastructure.port.out.TipoSolicitudRepositoryPort;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializa datos semilla en la base de datos al arrancar la aplicación
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepositoryPort rolRepository;
    private final TipoSolicitudRepositoryPort tipoSolicitudRepository;

    public DataInitializer(RolRepositoryPort rolRepository,
                           TipoSolicitudRepositoryPort tipoSolicitudRepository) {
        this.rolRepository = rolRepository;
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeTiposSolicitud();
        initializeAreas();
        initializeCargos();
    }

    /**
     * Inicializa los roles si no existen
     */
    private void initializeRoles() {
        if (rolRepository.findByNombre("ADMIN").isEmpty()) {
            Rol adminRol = new Rol();
            adminRol.setNombre("ADMIN");
            adminRol.setDescripcion("Administrador del sistema");
            rolRepository.save(adminRol);
            System.out.println("✅ Rol ADMIN creado");
        }

        if (rolRepository.findByNombre("SUPERVISOR").isEmpty()) {
            Rol supervisorRol = new Rol();
            supervisorRol.setNombre("SUPERVISOR");
            supervisorRol.setDescripcion("Supervisor de área");
            rolRepository.save(supervisorRol);
            System.out.println("✅ Rol SUPERVISOR creado");
        }

        if (rolRepository.findByNombre("FUNCIONARIO").isEmpty()) {
            Rol funcionarioRol = new Rol();
            funcionarioRol.setNombre("FUNCIONARIO");
            funcionarioRol.setDescripcion("Funcionario operativo");
            rolRepository.save(funcionarioRol);
            System.out.println("✅ Rol FUNCIONARIO creado");
        }
    }

    /**
     * Inicializa los tipos de solicitud si no existen
     */
    private void initializeTiposSolicitud() {
        if (tipoSolicitudRepository.findByNombre("CAMBIO").isEmpty()) {
            TipoSolicitud cambioTipo = new TipoSolicitud();
            cambioTipo.setNombre("CAMBIO");
            cambioTipo.setDescripcion("Solicitud de cambio de turno");
            tipoSolicitudRepository.save(cambioTipo);
            System.out.println("✅ Tipo CAMBIO creado");
        }

        if (tipoSolicitudRepository.findByNombre("ELIMINACION").isEmpty()) {
            TipoSolicitud eliminacionTipo = new TipoSolicitud();
            eliminacionTipo.setNombre("ELIMINACION");
            eliminacionTipo.setDescripcion("Solicitud de eliminación de turno");
            tipoSolicitudRepository.save(eliminacionTipo);
            System.out.println("✅ Tipo ELIMINACION creado");
        }
    }

    private void initializeAreas() {
        System.out.println("⏭️  Inicialización de áreas omitida (insertar por BD manual o CRUD)");
        // if (areaRepository.findByNombre("Servidores").isEmpty()) {
        //     Area area = new Area();
        //     area.setNombre("Servidores");
        //     area.setDescripcion("Área de gestión de servidores");
        //     area.setActivo(true);
        //     areaRepository.save(area);
        //     System.out.println("✅ Área SERVIDORES creada");
        // }
        //
        // if (areaRepository.findByNombre("Redes").isEmpty()) {
        //     Area area = new Area();
        //     area.setNombre("Redes");
        //     area.setDescripcion("Área de gestión de redes");
        //     area.setActivo(true);
        //     areaRepository.save(area);
        //     System.out.println("✅ Área REDES creada");
        // }
    }

    private void initializeCargos() {
        System.out.println("⏭️  Inicialización de cargos omitida (insertar por BD manual o CRUD)");
        // if (cargoRepository.findByNombre("Técnico").isEmpty()) {
        //     Cargo cargo = new Cargo();
        //     cargo.setNombre("Técnico");
        //     cargo.setDescripcion("Técnico especializado");
        //     cargo.setActivo(true);
        //     cargoRepository.save(cargo);
        //     System.out.println("✅ Cargo TÉCNICO creado");
        // }
        //
        // if (cargoRepository.findByNombre("Supervisor").isEmpty()) {
        //     Cargo cargo = new Cargo();
        //     cargo.setNombre("Supervisor");
        //     cargo.setDescripcion("Supervisor de equipo");
        //     cargo.setActivo(true);
        //     cargoRepository.save(cargo);
        //     System.out.println("✅ Cargo SUPERVISOR creado");
        // }
    }
}