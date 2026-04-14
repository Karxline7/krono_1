import { Component, OnInit, OnDestroy, signal, computed, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common'; // Para *ngIf y *ngFor
import { FormsModule } from '@angular/forms';   // Para [(ngModel)]
import { TurnoService, Turno } from '../../../domain/services/cartas-turnos/cartas-turnos';
import { AsignacionService } from '../../../domain/services/horario/horario';
import { Navbar } from "../../shared/navbar/navbar";
import { TableModule } from 'primeng/table';
import { forkJoin, finalize } from 'rxjs';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { RippleModule } from 'primeng/ripple';
import { DialogModule } from 'primeng/dialog';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';


@Component({
  selector: 'app-cartas-turnos',
  standalone: true, // Asegúrate de que esto esté presente si no usas NgModules
  imports: [CommonModule, FormsModule, Navbar, DialogModule, TableModule, ButtonModule, InputTextModule, InputNumberModule, CardModule, TagModule, TooltipModule, RippleModule],
  templateUrl: './cartas-turnos.html',
  styleUrls: ['./cartas-turnos.scss']
})
export class CartasTurnos implements OnInit, OnDestroy {
  turnos = signal<Turno[]>([]);
  accionActual: 'agregar' | 'editar' | null = null;
  turnoSeleccionado: Turno | null = null;
  syncInterval: any;
  
  cargando: boolean = false;

  verToast = false;
  mensajeToast = '';

  nuevoTurno: Turno = {
    nombre: '',
    horaInicio: '',
    horaFin: '',
    horaalmuerzo: '',
    horabreak: '',
    tipo: '',
    cantidadPersonas: 0 
  };

  constructor(
    private turnoService: TurnoService,
    private asignacionService: AsignacionService
  ) {}

  ngOnInit() {
    this.cargarTurnos();
    
    // Iniciamos el intervalo de actualización automática cada 5 segundos
    this.syncInterval = setInterval(() => {
      this.refresh();
    }, 5000);
  }

  ngOnDestroy() {
    // Limpieza vital para evitar que el proceso siga corriendo en segundo plano
    if (this.syncInterval) {
      clearInterval(this.syncInterval);
    }
  }

  refresh() {
    if (!this.accionActual) {
      this.cargarTurnos();
    }
  }

  cargarTurnos() {
    this.cargando = true;
    
    // Obtenemos la fecha de hoy para saber cuántas personas tienen el turno actualmente
    const hoy = new Date().toISOString().split('T')[0];

    forkJoin({
      turnos: this.turnoService.listar(),
      asignaciones: this.asignacionService.listarPorFecha(hoy).pipe(
        // Si hay error al listar asignaciones (ej. endpoint falla), retornamos arreglo vacío
        catchError(() => of([]))
      )
    }).pipe(
      finalize(() => this.cargando = false)
    ).subscribe({
      next: ({ turnos, asignaciones }) => {
        const conteoPorTurno = asignaciones.reduce((acc: Record<number, Set<number>>, asig: any) => {
          if (asig.turnoId) {
            if (!acc[asig.turnoId]) {
              acc[asig.turnoId] = new Set<number>();
            }
            if (asig.funcionarioId) {
              acc[asig.turnoId].add(asig.funcionarioId);
            }
          }
          return acc;
        }, {});

        const turnosMapeados = turnos.map(t => ({
          ...t,
          cantidadPersonas: t.id && conteoPorTurno[t.id] ? conteoPorTurno[t.id].size : 0
        }));
        this.turnos.set(turnosMapeados);
      },
      error: (err) => {
        console.error('Error al cargar los turnos:', err);
      }
    });
  }

  statsDinamicas = computed(() => {
    const resumen = this.turnos().reduce((acc: any, t) => {
      const nombre = t.nombre || 'Sin nombre';
      const personas = t.cantidadPersonas || 0;
      acc[nombre] = (acc[nombre] || 0) + personas;
      return acc;
    }, {});
    
    return Object.keys(resumen).map(key => ({
      nombre: key,
      cantidad: resumen[key]
    }));
  });

  onGuardar() {
    this.turnoService.crear(this.nuevoTurno).subscribe({
      next: () => {
        this.lanzarToast("Turno guardado correctamente");
        this.cargarTurnos();
        this.resetForms();
      },
      error: () => this.lanzarToast("Error al guardar el turno")
    });
  }

  resetForms() {
    this.accionActual = null;
    this.turnoSeleccionado = null;
    this.nuevoTurno = {
      nombre: '', horaInicio: '', horaFin: '',
      horaalmuerzo: '', horabreak: '', tipo: '', cantidadPersonas: 0
    };
  }

  onActualizar() {
    if (this.turnoSeleccionado?.id) {
      this.turnoService.editar(this.turnoSeleccionado.id, this.turnoSeleccionado).subscribe({
        next: () => {
          this.lanzarToast("Turno actualizado correctamente");
          this.cargarTurnos();
          this.resetForms();
        },
        error: () => this.lanzarToast("Error al actualizar el turno")
      });
    }
  }

  seleccionarTurno(turno: Turno, accion: 'editar' | 'eliminar') {
    if (accion === 'eliminar' && turno.id) {
      this.turnoService.eliminar(turno.id).subscribe({
        next: () => {
          this.lanzarToast("Turno eliminado correctamente");
          this.cargarTurnos();
        },
        error: () => this.lanzarToast("Error al eliminar el turno")
      });
    } else {
      this.turnoSeleccionado = { ...turno };
      this.accionActual = 'editar';
    }
  }

  lanzarToast(msg: string) {
    this.mensajeToast = msg;
    this.verToast = true;
    setTimeout(() => this.verToast = false, 3000);
  }
}