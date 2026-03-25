import { Component, OnInit } from '@angular/core';
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
  imports: [CommonModule, FormsModule, Navbar, TableModule, ButtonModule, InputTextModule, DialogModule, TagModule],
  templateUrl: './horario.html',
  styleUrl: './horario.scss',
})
export class Horario implements OnInit {

  private apiUrl = 'http://localhost:8081/api/asignaciones';

  semanas      = ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'];
  funcionarios: string[] = [];

  listaTurnosSemanales: TurnoSemanal[] = [];
  turnosDisponibles: any[] = [];
  usuariosDisponibles: any[] = [];

  // Filtros superiores
  fechaFiltro       = this.getUpcomingFriday();
  filtroSemana      = '';
  filtroTurno       = '';
  filtroFuncionario = '';

  getUpcomingFriday(): string {
    const d = new Date();
    const day = d.getDay(); // 0 is Sunday, 5 is Friday
    let diff = 5 - day;
    if (diff < 0) diff += 7;
    d.setDate(d.getDate() + diff);
    return d.toISOString().split('T')[0];
  }

  get turnosFiltrados(): TurnoSemanal[] {
    return this.listaTurnosSemanales.filter(t => {
      const okSemana      = !this.filtroSemana      || t.semana      === this.filtroSemana;
      const okFuncionario = !this.filtroFuncionario || t.funcionario === this.filtroFuncionario;
      const okTurno       = !this.filtroTurno || [
        t.lunes?.turnoNombre, t.martes?.turnoNombre, t.miercoles?.turnoNombre,
        t.jueves?.turnoNombre, t.viernes?.turnoNombre, t.sabado?.turnoNombre, t.domingo?.turnoNombre
      ].includes(this.filtroTurno);
      
      return okSemana && okTurno && okFuncionario;
    });
  }

  // Formulario
  turnoActual: Turno = this.inicializarTurno();
  mostrarFormulario  = false;
  esEdicion          = false;
  turnoIdSeleccionado: number | null = null;

  // Modal / Toast
  mostrarConfirmacion = false;
  verToast            = false;
  mensajeToast        = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarDatosMaestros();
  }

  cargarDatosMaestros() {
    forkJoin({
      turnos: this.http.get<any[]>('/api/turnos').pipe(catchError(() => of([]))),
      // Obtenemos usuarios de las areas principales asumiendo ID 1 y 2
      users1: this.http.get<any[]>('/api/usuarios/area/1').pipe(catchError(() => of([]))),
      users2: this.http.get<any[]>('/api/usuarios/area/2').pipe(catchError(() => of([])))
    }).subscribe(res => {
      this.turnosDisponibles = res.turnos;
      const allUsers = [...res.users1, ...res.users2];
      this.usuariosDisponibles = Array.from(new Map(allUsers.map(item => [item.id, item])).values());
      this.funcionarios = this.usuariosDisponibles.map(x => x.nombre);
      
      this.cargarTurnos();
    });
  }

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
      this.http.get<any[]>(`${this.apiUrl}/fecha/${f}`).pipe(catchError(() => of([])))
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

       const t = this.turnosDisponibles.find(x => x.id === a.turnoId);
       const tNombre = t ? t.nombre : 'Descanso';
       const esDescanso = t ? t.nombre === 'Descanso' : true;
       
       if (t && t.horabreak) w.break = t.horabreak;
       if (t && t.horaalmuerzo) w.almuerzo = t.horaalmuerzo;

       const dMes = a.fecha.split('-')[2];
       const diaObj: AsignacionDia = {
          id: a.id, turnoId: a.turnoId, turnoNombre: tNombre, diaMes: dMes, 
          esDescanso: esDescanso, break: t ? t.horabreak || '—' : '—', almuerzo: t ? t.horaalmuerzo || '—' : '—'
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

  onTurnoChange() {
    if (this.turnoActual.turnoId != null) {
       const t = this.turnosDisponibles.find(x => x.id === Number(this.turnoActual.turnoId));
       if (t) {
         this.turnoActual.turno = t.nombre;
         if (t.nombre === 'Descanso') {
           this.turnoActual.esDescanso = true;
           this.turnoActual.horaInicio = '';
           this.turnoActual.horaFin    = '';
         } else {
           this.turnoActual.esDescanso = false;
           this.turnoActual.horaInicio = t.horaInicio || '';
           this.turnoActual.horaFin    = t.horaFin || '';
         }
       }
    }
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) this.resetForm();
  }

  abrirEditar() {
    if (this.turnoIdSeleccionado !== null) {
      this.esEdicion         = true;
      this.mostrarFormulario = true;
    } else {
      this.lanzarToast('Selecciona un registro para editar');
    }
  }

  guardar() {
    if (!this.turnoActual.funcionarioId || !this.turnoActual.dia || !this.turnoActual.turnoId) {
      this.lanzarToast('Completa los campos obligatorios (Funcionario, Fecha, Turno)');
      return;
    }

    const payload = {
      funcionarioId: this.turnoActual.funcionarioId,
      turnoId: this.turnoActual.turnoId,
      fecha: this.turnoActual.dia,
      areaId: 1
    };

    if (this.esEdicion && this.turnoActual.id !== 0) {
      this.http.put(`${this.apiUrl}/${this.turnoActual.id}`, payload).subscribe({
        next: () => {
          this.lanzarToast('¡Registro actualizado!');
          this.cargarTurnos();
          this.toggleFormulario();
        },
        error: (error) => {
          console.error('Error al actualizar', error);
          this.lanzarToast('Error al actualizar');
        }
      });
    } else {
      this.http.post(this.apiUrl, payload).subscribe({
        next: () => {
          this.lanzarToast('¡Registro guardado!');
          this.cargarTurnos();
          this.toggleFormulario();
        },
        error: (error) => {
          console.error('Error al guardar', error);
          this.lanzarToast('Error al guardar');
        }
      });
    }
  }

  seleccionarCelda(diaObj: AsignacionDia | null, funcionarioId: number | null, fechaDia: string) {
    if (!funcionarioId) return;

    if (diaObj) {
      if (this.turnoIdSeleccionado === diaObj.id) {
        this.resetForm();
      } else {
        this.turnoIdSeleccionado = diaObj.id;
        
        let dSemana = '';
        const fObj = new Date(fechaDia + 'T00:00:00');
        const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
        dSemana = diasLista[fObj.getDay()];

        this.turnoActual = {
          id: diaObj.id,
          semana: 'Semana Actual',
          turno: diaObj.turnoNombre,
          funcionario: this.usuariosDisponibles.find(u => u.id === funcionarioId)?.nombre || '',
          dia: fechaDia,
          diaSemana: dSemana,
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

        this.esEdicion = false;
        this.mostrarFormulario = false;
      }
    } else {
      this.resetForm();
      this.turnoActual.funcionarioId = funcionarioId;
      this.turnoActual.dia = fechaDia;
      this.esEdicion = false;
      this.mostrarFormulario = true;
    }
  }

  getDiaMes(fechaStr: string): string {
    return fechaStr ? fechaStr.split('-')[2] : '';
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
      this.http.delete(`${this.apiUrl}/${this.turnoIdSeleccionado}`).subscribe({
        next: () => {
          this.lanzarToast('Eliminado correctamente');
          this.mostrarConfirmacion = false;
          this.cargarTurnos();
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al eliminar', error);
          this.lanzarToast('Error al eliminar');
          this.mostrarConfirmacion = false;
        }
      });
    }
  }

  cancelarEliminacion() {
    this.mostrarConfirmacion = false;
  }

  private inicializarTurno(): Turno {
    let dSemana = '';
    let dMes = '';
    if (this.fechaFiltro) {
       const [y, m, d] = this.fechaFiltro.split('-');
       const fObj = new Date(parseInt(y), parseInt(m) - 1, parseInt(d));
       const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
       dSemana = diasLista[fObj.getDay()];
       dMes = d;
    }
  
    return {
      id: 0, semana: '', turno: '', funcionario: '',
      dia: this.fechaFiltro, diaSemana: dSemana, diaMes: dMes, horaInicio: '', horaFin: '',
      esDescanso: false, break: '', almuerzo: '', compensatorios: '0', vacaciones: '0',
      funcionarioId: null, turnoId: null
    };
  }

  resetForm() {
    this.turnoActual         = this.inicializarTurno();
    this.esEdicion           = false;
    this.turnoIdSeleccionado = null;
    this.mostrarFormulario   = false;
  }

  lanzarToast(msg: string) {
    this.mensajeToast = msg;
    this.verToast     = true;
    setTimeout(() => (this.verToast = false), 3000);
  }
}