import { Component, OnInit, OnDestroy, signal, computed } from '@angular/core';
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
import { ReportesService } from '../../../domain/services/reportes/reportes';

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

  listaTurnosSemanales = signal<TurnoSemanal[]>([]);
  turnosDisponibles = signal<any[]>([]);
  usuariosDisponibles = signal<any[]>([]);

  // Filtros superiores
  fechaFiltro = this.getUpcomingFriday();
  filtroSemana = '';
  filtroTurno = '';
  filtroFuncionario = '';

  // Control de UI
  mostrarFormulario = false;
  esEdicion = false;
  celdasMap: Record<string, {id: number | null, funcionarioId: number, fecha: string, turnoNombre?: string}> = {};
  get celdasSeleccionadas() { return Object.values(this.celdasMap); }
  verToast = false;
  mensajeToast = '';
  mostrarConfirmacion = false;

  turnoActual: Turno = this.inicializarTurno();

  constructor(
    private http: HttpClient,
    private funcionarioService: FuncionarioService,
    private turnoService: TurnoService,
    private asignacionService: AsignacionService,
    private reportesService: ReportesService
  ) {}

  descargarReporte() {
    this.reportesService.descargarReporteGeneral().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_asignaciones.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      },
      error: (error) => {
        console.error('Error al descargar el reporte:', error);
      }
    });
  }

  private intervalId: any;

  ngOnInit() {
    this.cargarCatalogos();
    this.cargarTurnos(); // Llamamos inmediatamente para traer datos base

    // Iniciamos la sincronización cada 5 segundos (Background Polling)
    this.intervalId = setInterval(() => {
      this.refresh();
    }, 5000);
  }

  refresh() {
    // Si no hay formularios ni diálogos de confirmación abiertos, refrescamos
    if (!this.mostrarFormulario && !this.mostrarConfirmacion) {
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
        this.usuariosDisponibles.set(usuarios);
        this.funcionarios = usuarios.map(u => u.nombre);
        this.turnosDisponibles.set(turnos);
        
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

  turnosFiltrados = computed(() => {
    return this.listaTurnosSemanales().filter(t => {
      const okSemana = !this.filtroSemana || t.semana === this.filtroSemana;
      const okFuncionario = !this.filtroFuncionario || t.funcionario === this.filtroFuncionario;
      const okTurno = !this.filtroTurno || [
        t.lunes?.turnoNombre, t.martes?.turnoNombre, t.miercoles?.turnoNombre,
        t.jueves?.turnoNombre, t.viernes?.turnoNombre, t.sabado?.turnoNombre, t.domingo?.turnoNombre
      ].includes(this.filtroTurno);
      
      return okSemana && okTurno && okFuncionario;
    });
  });

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
        this.listaTurnosSemanales.set([]);
      }
    });
  }

  procesarTurnosSemanales(asignacionesFlat: any[], fechasSemana: string[]) {
    const map = new Map<number, TurnoSemanal>();

    this.usuariosDisponibles().forEach(u => {
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

      const t = this.turnosDisponibles().find(x => Number(x.id) === Number(a.turnoId));
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
          horaInicio: t ? t.horaInicio || '—' : '—',
          horaFin: t ? t.horaFin || '—' : '—'
       };

       if (a.fecha === fechasSemana[0]) w.lunes = diaObj;
       else if (a.fecha === fechasSemana[1]) w.martes = diaObj;
       else if (a.fecha === fechasSemana[2]) w.miercoles = diaObj;
       else if (a.fecha === fechasSemana[3]) w.jueves = diaObj;
       else if (a.fecha === fechasSemana[4]) w.viernes = diaObj;
       else if (a.fecha === fechasSemana[5]) w.sabado = diaObj;
       else if (a.fecha === fechasSemana[6]) w.domingo = diaObj;
    });

    this.listaTurnosSemanales.set(Array.from(map.values()));
  }

  // --- MÉTODOS DE ACCIÓN ---
  onTurnoChange() {
    if (this.turnoActual.turnoId != null) {
      const t = this.turnosDisponibles().find(x => Number(x.id) === Number(this.turnoActual.turnoId));
      if (t) {
        this.turnoActual.horaInicio = t.horaInicio || '';
        this.turnoActual.horaFin = t.horaFin || '';
        this.turnoActual.break = t.horabreak || '';
        this.turnoActual.almuerzo = t.horaalmuerzo || '';
        this.turnoActual.esDescanso = t.nombre.toLowerCase().includes('descanso');
      }
    }
  }

  isCeldaSeleccionada(funcionarioId: number | null, fecha: string): boolean {
    if (!funcionarioId) return false;
    return !!this.celdasMap[`${funcionarioId}_${fecha}`];
  }

  seleccionarCelda(diaObj: AsignacionDia | null, funcionarioId: number | null, fechaDia: string) {
    if (!funcionarioId) return;

    const key = `${funcionarioId}_${fechaDia}`;

    if (this.celdasMap[key]) {
      delete this.celdasMap[key];
    } else {
      const currentSelected = this.celdasSeleccionadas;
      if (currentSelected.length > 0) {
        const first = currentSelected[0];
        const sameRow = currentSelected.every(c => c.funcionarioId === funcionarioId);
        const sameCol = currentSelected.every(c => c.fecha === fechaDia);

        if (!sameRow && !sameCol) {
          this.celdasMap = {};
        } else if (sameRow && !sameCol) {
          if (funcionarioId !== first.funcionarioId) this.celdasMap = {};
        } else if (sameCol && !sameRow) {
          if (fechaDia !== first.fecha) this.celdasMap = {};
        } else {
          if (funcionarioId !== first.funcionarioId && fechaDia !== first.fecha) {
              this.celdasMap = {};
          }
        }
      }
      this.celdasMap[`${funcionarioId}_${fechaDia}`] = {
        id: diaObj ? diaObj.id : null,
        funcionarioId,
        fecha: fechaDia,
        turnoNombre: diaObj?.turnoNombre
      };
    }

    this.actualizarEstadoFormulario();
  }

  seleccionarFila(t: any) {
    this.celdasMap = {};
    if (!t || !t.funcionarioId) return;
    this.diasSemana.forEach(dia => {
        const diaObj = t[dia];
        const fecha = t.fechas[dia];
        this.celdasMap[`${t.funcionarioId}_${fecha}`] = {
           id: diaObj ? diaObj.id : null,
           funcionarioId: t.funcionarioId,
           fecha: fecha,
           turnoNombre: diaObj?.turnoNombre
        };
    });
    this.actualizarEstadoFormulario();
  }

  seleccionarColumna(dia: string) {
    this.celdasMap = {};
    this.turnosFiltrados().forEach((t: any) => {
        const diaObj = t[dia];
        const fecha = t.fechas[dia];
        if(t.funcionarioId) {
          this.celdasMap[`${t.funcionarioId}_${fecha}`] = {
             id: diaObj ? diaObj.id : null,
             funcionarioId: t.funcionarioId,
             fecha: fecha,
             turnoNombre: diaObj?.turnoNombre
          };
        }
    });
    this.actualizarEstadoFormulario();
  }

  actualizarEstadoFormulario() {
    const seleccionadas = this.celdasSeleccionadas;
    if (seleccionadas.length === 0) {
      this.resetForm();
    } else if (seleccionadas.length === 1) {
      const celda = seleccionadas[0];
      const fObj = new Date(celda.fecha + 'T00:00:00');
      const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
      const turnoAsignado = this.turnosDisponibles().find(t => t.nombre === celda.turnoNombre);

      this.turnoActual = {
        ...this.inicializarTurno(),
        id: celda.id || 0,
        turno: celda.turnoNombre || '',
        funcionario: this.usuariosDisponibles().find(u => u.id === celda.funcionarioId)?.nombre || '',
        dia: celda.fecha,
        diaSemana: diasLista[fObj.getDay()],
        funcionarioId: celda.funcionarioId,
        turnoId: turnoAsignado ? turnoAsignado.id : null
      };
      this.onTurnoChange();
      this.esEdicion = !!celda.id;
      this.mostrarFormulario = true;
    } else {
      this.turnoActual = this.inicializarTurno();
      this.esEdicion = seleccionadas.some(c => c.id !== null);
      this.mostrarFormulario = true;
    }
  }

  abrirEditar() {
    if (this.celdasSeleccionadas.length > 0) {
      this.esEdicion = true;
      this.mostrarFormulario = true;
    } else {
      this.lanzarToast('Selecciona turnos en la tabla para editar');
    }
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) this.resetForm();
  }

  onGuardar() {
    const seleccionadas = this.celdasSeleccionadas;
    if (seleccionadas.length === 0) return;

    if (!this.turnoActual.turnoId) {
      this.lanzarToast('Por favor, selecciona un turno.');
      return;
    }

    const requests = seleccionadas.map(celda => {
      if (celda.id) {
        return this.asignacionService.editar(Number(celda.id), {
          nuevoFuncionarioId: Number(celda.funcionarioId),
          nuevoTurnoId: Number(this.turnoActual.turnoId),
          nuevaFecha: celda.fecha
        }).pipe(catchError(() => of(null)));
      } else {
        return this.asignacionService.crear({
          funcionarioId: Number(celda.funcionarioId),
          turnoId: Number(this.turnoActual.turnoId),
          fecha: celda.fecha
        }).pipe(catchError(() => of(null)));
      }
    });

    forkJoin(requests).subscribe({
      next: () => {
        this.lanzarToast('¡Guardado con éxito!');
        this.cargarTurnos();
        this.resetForm();
      },
      error: () => this.lanzarToast('Error al guardar')
    });
  }

  abrirModalEliminar() {
    if (this.celdasSeleccionadas.some(c => c.id !== null)) {
      this.mostrarConfirmacion = true;
    } else {
      this.lanzarToast('Selecciona registros guardados para eliminar');
    }
  }

  eliminar() {
    const ids = this.celdasSeleccionadas.filter(c => c.id !== null).map(c => c.id as number);
    if (ids.length > 0) {
      const requests = ids.map(id => this.asignacionService.eliminar(id).pipe(catchError(() => of(null))));
      forkJoin(requests).subscribe({
        next: () => {
          this.lanzarToast('Eliminados correctamente');
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
    } else {
       this.mostrarConfirmacion = false;
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
    this.celdasMap = {};
    this.mostrarFormulario = false;
  }

  lanzarToast(msg: string) {
    // Usamos setTimeout para evitar el error ExpressionChangedAfterItHasBeenCheckedError
    setTimeout(() => {
      this.mensajeToast = msg;
      this.verToast = true;
      setTimeout(() => (this.verToast = false), 3000);
    }, 0);
  }


}