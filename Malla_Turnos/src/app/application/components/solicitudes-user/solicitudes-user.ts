import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';
import { ButtonModule } from 'primeng/button';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-solicitudes-user',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarUser, ButtonModule],
  templateUrl: './solicitudes-user.html',
  styleUrl: './solicitudes-user.scss',
})
export class SolicitudesUser implements OnInit {
  private apiUrl = 'http://localhost:8081/api/solicitudes';
  private asignacionesUrl = 'http://localhost:8081/api/asignaciones';
  private turnosUrl = 'http://localhost:8081/api/turnos';
  private tiposSolicitudUrl = 'http://localhost:8081/api/tipos-solicitud';

  // El usuario actual para el que se gestionan las solicitudes (Funcionario ID = 1)
  funcionarioId = 1;

  nuevaSolicitud = {
    asignacionTurnoId: '',
    tipo: '',
    descripcion: ''
  };

  misAsignaciones = signal<any[]>([]);
  turnosDisponibles: any[] = [];
  tiposSolicitud = signal<any[]>([]);

  verToast = false;
  mensajeToast = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    const fechas = this.generarFechasSemana();

    // Crear peticiones para cada día de la semana (técnica compatible con el backend actual)
    const asignacionesRequests = fechas.map(f =>
      this.http.get<any[]>(`${this.asignacionesUrl}/fecha/${f}`).pipe(catchError(() => of([])))
    );

    forkJoin({
      asignacionesPorDia: forkJoin(asignacionesRequests),
      turnos: this.http.get<any[]>(this.turnosUrl).pipe(catchError(() => of([]))),
      tipos: this.http.get<any[]>(this.tiposSolicitudUrl).pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ asignacionesPorDia, turnos, tipos }) => {
        this.turnosDisponibles = turnos;
        this.tiposSolicitud.set(tipos);

        // Aplanar todos los días de asignaciones en un solo array
        const todasAsignaciones = asignacionesPorDia.flat();

        // Filtrar asignaciones SOLO PARA EL USUARIO ACTUAL (Funcionario 1)
        const asignacionesUsuario = todasAsignaciones.filter(a => {

          const fId = a.funcionarioId !== undefined ? a.funcionarioId : (a.funcionario_id || a.idFuncionario);
          return Number(fId) === Number(this.funcionarioId);
        });

        // Ordenar por fecha para mejor presentación
        asignacionesUsuario.sort((a, b) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime());

        this.misAsignaciones.set(asignacionesUsuario.map(a => {
          const turno = this.turnosDisponibles.find(t => Number(t.id) === Number(a.turnoId || a.turno_id));
          return {
            id: a.id,
            fecha: a.fecha,
            turnoNombre: turno ? turno.nombre : 'Turno',
            label: `${a.fecha} | ${turno ? turno.nombre : 'Turno'}`
          };
        }));
      },
      error: (error) => {
        console.error('Error al cargar datos para solicitudes', error);
      }
    });
  }

  // Helper para generar las fechas de la semana de programación (Viernes base)
  generarFechasSemana(): string[] {
    const d = new Date();
    const day = d.getDay();
    let diff = 5 - day;
    if (diff < 0) diff += 7;
    d.setDate(d.getDate() + diff);

    const baseDate = new Date(d.toISOString().split('T')[0] + 'T00:00:00');
    const dayOfWeek = baseDate.getDay();
    const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;

    const monday = new Date(baseDate.getTime());
    monday.setDate(baseDate.getDate() + diffToMonday);

    const fechas: string[] = [];
    for (let i = 0; i < 14; i++) { // Traemos 14 días (2 semanas) para dar margen al usuario
      const dSemana = new Date(monday.getTime());
      dSemana.setDate(monday.getDate() + i);
      fechas.push(dSemana.toISOString().split('T')[0]);
    }
    return fechas;
  }

  enviarSolicitud() {
    if (!this.nuevaSolicitud.asignacionTurnoId || !this.nuevaSolicitud.tipo || !this.nuevaSolicitud.descripcion) {
      this.lanzarToast('Error: Por favor llene todos los campos, asegurándose de seleccionar un turno');
      return;
    }

    const payload = {
      asignacionTurnoId: Number(this.nuevaSolicitud.asignacionTurnoId),
      tipoSolicitudId: Number(this.nuevaSolicitud.tipo),
      motivoSolicitud: this.nuevaSolicitud.descripcion,
      estado: 'PENDIENTE'
    };

    this.http.post(`${this.apiUrl}/solicitud`, payload).subscribe({
      next: () => {
        this.lanzarToast('Solicitud enviada correctamente');
        this.nuevaSolicitud = { asignacionTurnoId: '', tipo: '', descripcion: '' };
      },
      error: (error) => {
        console.error('Error al enviar la solicitud', error);
        this.lanzarToast('Error: Hubo un error al enviar la solicitud');
      }
    });
  }

  lanzarToast(msg: string) {
    this.mensajeToast = msg;
    this.verToast = true;
    setTimeout(() => this.verToast = false, 3000);
  }
}