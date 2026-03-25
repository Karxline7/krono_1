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

@Component({
  selector: 'app-horario',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar, TableModule, ButtonModule, InputTextModule, DialogModule, TagModule],
  templateUrl: './horario.html',
  styleUrl: './horario.scss',
})
export class Horario implements OnInit {

  private apiUrl = '/api/asignaciones';

  semanas      = ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'];
  funcionarios: string[] = [];

  listaTurnos: Turno[] = [];
  turnosDisponibles: any[] = [];
  usuariosDisponibles: any[] = [];

  // Filtros superiores
  fechaFiltro       = new Date().toISOString().split('T')[0];
  filtroSemana      = '';
  filtroTurno       = '';
  filtroFuncionario = '';

  get turnosFiltrados(): Turno[] {
    return this.listaTurnos.filter(t => {
      const okSemana      = !this.filtroSemana      || t.semana      === this.filtroSemana;
      const okTurno       = !this.filtroTurno       || t.turno       === this.filtroTurno;
      const okFuncionario = !this.filtroFuncionario || t.funcionario === this.filtroFuncionario;
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

    this.http.get<any[]>(`${this.apiUrl}/fecha/${this.fechaFiltro}`).subscribe({
      next: (data) => {
        this.listaTurnos = data.map(asignacion => {
           const u = this.usuariosDisponibles.find(x => x.id === asignacion.funcionarioId);
           const t = this.turnosDisponibles.find(x => x.id === asignacion.turnoId);

           let dSemana = '';
           let dMes = '';
           if (asignacion.fecha) {
             const partes = asignacion.fecha.split('-');
             const fObj = new Date(parseInt(partes[0]), parseInt(partes[1]) - 1, parseInt(partes[2]));
             const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
             dSemana = diasLista[fObj.getDay()];
             dMes = partes[2];
           }

           return {
             id: asignacion.id,
             semana: 'Semana Actual',
             turno: t ? t.nombre : 'Descanso',
             funcionario: u ? u.nombre : 'Usuario ' + asignacion.funcionarioId,
             dia: asignacion.fecha,
             diaSemana: dSemana,
             diaMes: dMes,
             horaInicio: t ? t.horaInicio : '',
             horaFin: t ? t.horaFin : '',
             esDescanso: t ? t.nombre === 'Descanso' : true,
             break: t && t.horabreak ? t.horabreak : '—',
             almuerzo: t && t.horaalmuerzo ? t.horaalmuerzo : '—',
             compensatorios: '0',
             vacaciones: '0',
             funcionarioId: asignacion.funcionarioId,
             turnoId: asignacion.turnoId
           };
        });
      },
      error: (error) => {
        console.error('Error al cargar asignaciones', error);
        this.listaTurnos = [];
      }
    });
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

  seleccionarFila(turno: Turno) {
    if (this.turnoIdSeleccionado === turno.id) {
      this.resetForm();
    } else {
      this.turnoIdSeleccionado = turno.id;
      this.turnoActual        = { ...turno };
      if (!this.turnoActual.dia) {
        this.turnoActual.dia = this.fechaFiltro;
      }
      this.esEdicion          = false;
      this.mostrarFormulario  = false;
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
       const partes = this.fechaFiltro.split('-');
       if (partes.length === 3) {
         const fObj = new Date(parseInt(partes[0]), parseInt(partes[1]) - 1, parseInt(partes[2]));
         const diasLista = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
         dSemana = diasLista[fObj.getDay()];
         dMes = partes[2];
       }
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