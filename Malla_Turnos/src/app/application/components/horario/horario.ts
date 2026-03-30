import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../shared/navbar/navbar';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { FuncionarioService } from '../../../domain/services/Funcionarios/funcionarios';
import { TurnoService } from '../../../domain/services/cartas-turnos/cartas-turnos';
import { AsignacionService } from '../../../domain/services/horario/horario';

// --- INTERFACES ---
export interface Turno {
  id: number;
  semana: string;
  turno: string;
  funcionario: string;
  dia: string;
  diaSemana: string;
  diaMes: string;
  horaInicio: string;
  horaFin: string;
  esDescanso: boolean;
  break: string;
  almuerzo: string;
  compensatorios: string;
  vacaciones: string;
  funcionarioId?: number | null;
  turnoId?: number | null;
}

export interface AsignacionDia {
  id: number;
  turnoId: number;
  turnoNombre: string;
  diaMes: string;
  esDescanso: boolean;
  break: string;
  almuerzo: string;
  horaInicio: string;  // Added
  horaFin: string;    // Added
}

export interface TurnoSemanal {
  funcionarioId: number;
  funcionario: string;
  semana: string;
  lunes: AsignacionDia | null;
  martes: AsignacionDia | null;
  miercoles: AsignacionDia | null;
  jueves: AsignacionDia | null;
  viernes: AsignacionDia | null;
  sabado: AsignacionDia | null;
  domingo: AsignacionDia | null;
  fechas: {
    lunes: string; martes: string; miercoles: string; jueves: string; viernes: string; sabado: string; domingo: string;
  };
  break: string;
  almuerzo: string;
  compensatorios: string;
  vacaciones: string;
}

@Component({
  selector: 'app-horario',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    DialogModule,
    TagModule, 
    Navbar
  ],
  templateUrl: './horario.html',
  styleUrls: ['./horario.scss']
})
export class Horario implements OnInit, OnDestroy {
  
  // --- PROPIEDADES ---
  apiUrl = 'http://localhost:8081/api/asignaciones'; // Cambia esto por tu URL real
  semanas = ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'];
  diasSemana = ['lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado', 'domingo'] as const;
  funcionarios: string[] = [];

  listaTurnosSemanales: TurnoSemanal[] = [];
  turnosDisponibles: any[] = [];
  usuariosDisponibles: any[] = [];

  // Filtros superiores
  fechaFiltro = this.getUpcomingFriday();
  filtroSemana = '';
  filtroTurno = '';
  filtroFuncionario = '';

  // Control de UI
  mostrarFormulario = false;
  esEdicion = false;
  turnoIdSeleccionado: number | null = null;
  verToast = false;
  mensajeToast = '';
  mostrarConfirmacion = false;

  turnoActual: Turno = this.inicializarTurno();

  constructor(
    private http: HttpClient,
    private funcionarioService: FuncionarioService,
    private turnoService: TurnoService,
    private asignacionService: AsignacionService
  ) {}

  private intervalId: any;

  ngOnInit() {
    this.cargarCatalogos();
    this.cargarTurnos();

    // Iniciamos la sincronización cada 5 segundos (Background Polling)
    this.intervalId = setInterval(() => {
      this.refresh();
    }, 5000);
  }

refresh() {
  if (!this.mostrarFormulario && (!this.mostrarConfirmacion)) {
    this.cargarTurnos();
  }
}

  ngOnDestroy() {
  if (this.intervalId) {
    clearInterval(this.intervalId);
  }
}

  cargarCatalogos() {
    forkJoin({
      usuarios: this.funcionarioService.listar().pipe(catchError(() => of([]))),
      turnos: this.turnoService.listar().pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ usuarios, turnos }) => {
        this.usuariosDisponibles = usuarios;
        this.funcionarios = usuarios.map(u => u.nombre);
        this.turnosDisponibles = turnos;
        
        // Una vez cargados los catálogos, se cargan los turnos de la semana
        this.cargarTurnos();
      },
      error: (error) => {
        console.error('Error al cargar catálogos', error);
        this.lanzarToast('Error al cargar catálogos');
      }
    });
  }

  // --- LÓGICA DE FECHAS ---
  getUpcomingFriday(): string {
    const d = new Date();
    const day = d.getDay();
    let diff = 5 - day;
    if (diff < 0) diff += 7;
    d.setDate(d.getDate() + diff);
    return d.toISOString().split('T')[0];
  }

  get turnosFiltrados(): TurnoSemanal[] {
    return this.listaTurnosSemanales.filter(t => {
      const okSemana = !this.filtroSemana || t.semana === this.filtroSemana;
      const okFuncionario = !this.filtroFuncionario || t.funcionario === this.filtroFuncionario;
      const okTurno = !this.filtroTurno || [
        t.lunes?.turnoNombre, t.martes?.turnoNombre, t.miercoles?.turnoNombre,
        t.jueves?.turnoNombre, t.viernes?.turnoNombre, t.sabado?.turnoNombre, t.domingo?.turnoNombre
      ].includes(this.filtroTurno);
      
      return okSemana && okTurno && okFuncionario;
    });
  }

  // --- OPERACIONES API ---
  cargarTurnos() {
    if (!this.fechaFiltro) return;

    const baseDate = new Date(this.fechaFiltro + 'T00:00:00'); 
    const dayOfWeek = baseDate.getDay();
    const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
    
    const monday = new Date(baseDate.getTime());
    monday.setDate(baseDate.getDate() + diffToMonday);

    const fechasSemana: string[] = [];
    for(let i=0; i<7; i++) {
      const d = new Date(monday.getTime());
      d.setDate(monday.getDate() + i);
      fechasSemana.push(d.toISOString().split('T')[0]);
    }

    const requests = fechasSemana.map(f => 
      this.asignacionService.listarPorFecha(f).pipe(catchError(() => of([])))
    );

    forkJoin(requests).subscribe({
      next: (results) => {
        const flatAssignments = results.flat();
        this.procesarTurnosSemanales(flatAssignments, fechasSemana);
      },
      error: (error) => {
        console.error('Error al cargar asignaciones', error);
        this.listaTurnosSemanales = [];
      }
    });
  }

  procesarTurnosSemanales(asignacionesFlat: any[], fechasSemana: string[]) {
    const map = new Map<number, TurnoSemanal>();

    this.usuariosDisponibles.forEach(u => {
      map.set(u.id, {
        funcionarioId: u.id,
        funcionario: u.nombre,
        semana: 'Semana Actual',
        lunes: null, martes: null, miercoles: null, jueves: null, viernes: null, sabado: null, domingo: null,
        fechas: {
          lunes: fechasSemana[0], martes: fechasSemana[1], miercoles: fechasSemana[2], 
          jueves: fechasSemana[3], viernes: fechasSemana[4], sabado: fechasSemana[5], domingo: fechasSemana[6]
        },
        break: '—', almuerzo: '—', compensatorios: '0', vacaciones: '0'
      });
    });

    asignacionesFlat.forEach(a => {
      const w = map.get(a.funcionarioId);
      if (!w) return;

      const t = this.turnosDisponibles.find(x => Number(x.id) === Number(a.turnoId));
      if (!t) {
        return;
      }

       const tNombre = t.nombre;
       const esDescanso = tNombre.toLowerCase().includes('descanso');
       
       if (t && t.horabreak) w.break = t.horabreak;
       if (t && t.horaalmuerzo) w.almuerzo = t.horaalmuerzo;

       const dMes = a.fecha.split('-')[2];
       const diaObj: AsignacionDia = {
          id: a.id, turnoId: a.turnoId, turnoNombre: tNombre, diaMes: dMes, 
          esDescanso: esDescanso, break: t ? t.horabreak || '—' : '—', 
          almuerzo: t ? t.horaalmuerzo || '—' : '—',
          horaInicio: t ? t.horainicio || '—' : '—',
          horaFin: t ? t.horafin || '—' : '—'
       };

       if (a.fecha === fechasSemana[0]) w.lunes = diaObj;
       else if (a.fecha === fechasSemana[1]) w.martes = diaObj;
       else if (a.fecha === fechasSemana[2]) w.miercoles = diaObj;
       else if (a.fecha === fechasSemana[3]) w.jueves = diaObj;
       else if (a.fecha === fechasSemana[4]) w.viernes = diaObj;
       else if (a.fecha === fechasSemana[5]) w.sabado = diaObj;
       else if (a.fecha === fechasSemana[6]) w.domingo = diaObj;
    });

    this.listaTurnosSemanales = Array.from(map.values());
  }

  // --- MÉTODOS DE ACCIÓN ---
  onTurnoChange() {
    if (this.turnoActual.turnoId != null) {
      const t = this.turnosDisponibles.find(x => Number(x.id) === Number(this.turnoActual.turnoId));
      if (t) {
        this.turnoActual.horaInicio = t.horainicio || '';
        this.turnoActual.horaFin = t.horafin || '';
        this.turnoActual.break = t.horabreak || '';
        this.turnoActual.almuerzo = t.horaalmuerzo || '';
        this.turnoActual.esDescanso = t.nombre.toLowerCase().includes('descanso');
      }
    }
  }

  seleccionarCelda(diaObj: AsignacionDia | null, funcionarioId: number | null, fechaDia: string) {
    if (!funcionarioId) return;

    if (diaObj) {
      // Si ya estaba seleccionado este mismo ID, lo deseleccionamos
      if (this.turnoIdSeleccionado === diaObj.id && this.mostrarFormulario) {
        this.resetForm();
      } else {
        this.turnoIdSeleccionado = diaObj.id;
        const fObj = new Date(fechaDia + 'T00:00:00');
        const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];

        this.turnoActual = {
          id: diaObj.id,
          semana: 'Semana Actual',
          turno: diaObj.turnoNombre,
          funcionario: this.usuariosDisponibles.find(u => u.id === funcionarioId)?.nombre || '',
          dia: fechaDia,
          diaSemana: diasLista[fObj.getDay()],
          diaMes: diaObj.diaMes,
          horaInicio: '',
          horaFin: '',
          esDescanso: diaObj.esDescanso,
          break: diaObj.break,
          almuerzo: diaObj.almuerzo,
          compensatorios: '0',
          vacaciones: '0',
          funcionarioId: funcionarioId,
          turnoId: diaObj.turnoId
        };
        this.onTurnoChange();
        this.esEdicion = true;
        this.mostrarFormulario = true; // Abrir formulario inmediatamente como en funcionarios.ts
      }
    } else {
      // Es una celda vacía, preparamos para crear
      this.resetForm();
      this.turnoActual.funcionarioId = funcionarioId;
      this.turnoActual.dia = fechaDia;
      this.esEdicion = false;
      this.mostrarFormulario = true;
    }
  }

  abrirEditar() {
    if (this.turnoIdSeleccionado) {
      this.esEdicion = true;
      this.mostrarFormulario = true;
    } else {
      this.lanzarToast('Selecciona un turno en la tabla para editar');
    }
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) this.resetForm();
  }

  onGuardar() {
    // Asegurar tipos correctos para el backend como en funcionarios.ts
    const payload: any = {
      funcionarioId: Number(this.turnoActual.funcionarioId),
      turnoId: Number(this.turnoActual.turnoId),
      fecha: this.turnoActual.dia
    };

    if (this.esEdicion && this.turnoActual.id) {
      payload.id = this.turnoActual.id; // Solo incluimos ID si es actualización
      this.asignacionService.editar(this.turnoActual.id, payload).subscribe({
        next: () => {
          this.lanzarToast('¡Actualizado con éxito!');
          this.cargarTurnos();
          this.resetForm();
        },
        error: () => this.lanzarToast('Error al actualizar')
      });
    } else {
      this.asignacionService.crear(payload).subscribe({
        next: () => {
          this.lanzarToast('¡Guardado con éxito!');
          this.cargarTurnos();
          this.resetForm();
        },
        error: () => this.lanzarToast('Error al guardar')
      });
    }
  }

  abrirModalEliminar() {
    if (this.turnoIdSeleccionado !== null) {
      this.mostrarConfirmacion = true;
    } else {
      this.lanzarToast('Selecciona un registro para eliminar');
    }
  }

  eliminar() {
    if (this.turnoIdSeleccionado !== null) {
      this.asignacionService.eliminar(this.turnoIdSeleccionado).subscribe({
        next: () => {
          this.lanzarToast('Eliminado correctamente');
          this.mostrarConfirmacion = false;
          this.cargarTurnos();
          this.resetForm();
        },
        error: (err) => {
          console.error(err);
          this.lanzarToast('Error al eliminar');
          this.mostrarConfirmacion = false;
        }
      });
    }
  }

  cancelarEliminacion() {
    this.mostrarConfirmacion = false;
  }

  getDiaMes(fechaStr: string): string {
    return fechaStr ? fechaStr.split('-')[2] : '';
  }

  private inicializarTurno(): Turno {
    return {
      id: 0, semana: '', turno: '', funcionario: '',
      dia: this.fechaFiltro, diaSemana: '', diaMes: '', horaInicio: '', horaFin: '',
      esDescanso: false, break: '', almuerzo: '', compensatorios: '0', vacaciones: '0',
      funcionarioId: null, turnoId: null
    };
  }

  resetForm() {
    this.turnoActual = this.inicializarTurno();
    this.esEdicion = false;
    this.turnoIdSeleccionado = null;
    this.mostrarFormulario = false;
  }

  lanzarToast(msg: string) {
    this.mensajeToast = msg;
    this.verToast = true;
    setTimeout(() => (this.verToast = false), 3000);
  }


}