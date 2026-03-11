package com.datacenter.mallaturnos.application.UseCase.asignacion;

import com.datacenter.mallaturnos.domain.model.AsignacionTurno;
import com.datacenter.mallaturnos.port.out.AsignacionRepositoryPort;
import com.datacenter.mallaturnos.port.out.TurnoRepositoryPort;
import com.datacenter.mallaturnos.port.out.UsuarioRepositoryPort;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EditarTurnoAsignadoUseCase {

    private final AsignacionRepositoryPort asignacionRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final TurnoRepositoryPort turnoRepository;

    public EditarTurnoAsignadoUseCase(AsignacionRepositoryPort asignacionRepository,
                                     UsuarioRepositoryPort usuarioRepository,
                                     TurnoRepositoryPort turnoRepository) {
        this.asignacionRepository = asignacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
    }

    /**
     * Edita una asignación de turno
     * @param id ID de la asignación
     * @param nuevoFuncionarioId Nuevo funcionario
     * @param nuevaFecha Nueva fecha
     * @param nuevoTurnoId Nuevo turno (puede ser null para día libre)
     * @return Asignación editada
     */
    public AsignacionTurno ejecutar(Long id, Long nuevoFuncionarioId, LocalDate nuevaFecha,
                                    Long nuevoTurnoId) {
        
        // Obtener asignación existente
        Optional<AsignacionTurno> asignacionExistente = asignacionRepository.findById(id);
        
        if (!asignacionExistente.isPresent()) {
            throw new IllegalArgumentException("Asignación no encontrada");
        }

        // Validar que el nuevo funcionario existe
        if (!usuarioRepository.findById(nuevoFuncionarioId).isPresent()) {
            throw new IllegalArgumentException("Funcionario no encontrado");
        }

        // Validar que el nuevo turno existe (si no es null)
        if (nuevoTurnoId != null && !turnoRepository.findById(nuevoTurnoId).isPresent()) {
            throw new IllegalArgumentException("Turno no encontrado");
        }

        // Validar que no existe otra asignación en la misma fecha para el nuevo funcionario
        // (a menos que sea la misma asignación que estamos editando)
        Optional<AsignacionTurno> conflicto = asignacionRepository.findByFuncionarioAndFecha(nuevoFuncionarioId, nuevaFecha);
        if (conflicto.isPresent() && !conflicto.get().getId().equals(id)) {
            throw new IllegalArgumentException("El funcionario ya tiene asignación en esa fecha");
        }
         
        // Actualizar asignación
        AsignacionTurno asignacion = asignacionExistente.get();
        asignacion.setFuncionarioId(nuevoFuncionarioId);
        asignacion.setFecha(nuevaFecha);
        asignacion.setTurnoId(nuevoTurnoId);

        return asignacionRepository.save(asignacion);   

    }   

}
