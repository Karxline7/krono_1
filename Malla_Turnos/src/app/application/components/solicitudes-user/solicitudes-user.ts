import { Component, OnInit } from '@angular/core';
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

  // El usuario actual para el que se gestionan las solicitudes (Funcionario ID = 1)
  funcionarioId = 1;

  nuevaSolicitud = {
    asignacionTurnoId: '',
    tipo: '',
    descripcion: ''
  };

  misAsignaciones: any[] = [];
  turnosDisponibles: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    forkJoin({
      asignaciones: this.http.get<any[]>(this.asignacionesUrl).pipe(catchError(() => of([]))),
      turnos: this.http.get<any[]>(this.turnosUrl).pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ asignaciones, turnos }) => {
        this.turnosDisponibles = turnos;
        
        // Filtrar asignaciones SOLO PARA EL USUARIO ACTUAL (Funcionario 1)
        // Usamos una comparación flexible con Number() y soporta varios nombres de campo
        const asignacionesUsuario = asignaciones.filter(a => {
          const fId = a.funcionarioId !== undefined ? a.funcionarioId : (a.funcionario_id || a.idFuncionario);
          return Number(fId) === Number(this.funcionarioId);
        });
        
        // Ordenar por fecha para mejor presentación
        asignacionesUsuario.sort((a, b) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime());
        
        this.misAsignaciones = asignacionesUsuario.map(a => {
          const turno = this.turnosDisponibles.find(t => Number(t.id) === Number(a.turnoId || a.turno_id));
          return {
            id: a.id,
            fecha: a.fecha,
            turnoNombre: turno ? turno.nombre : 'Turno',
            label: `${a.fecha} | ${turno ? turno.nombre : 'Turno'}`
          };
        });
      },
      error: (error) => {
        console.error('Error al cargar datos para solicitudes', error);
      }
    });
  }

  enviarSolicitud() {
    if (!this.nuevaSolicitud.asignacionTurnoId || !this.nuevaSolicitud.tipo || !this.nuevaSolicitud.descripcion) {
      alert('Por favor llene todos los campos, asegurándose de seleccionar un turno');
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
        alert('Solicitud enviada correctamente');
        this.nuevaSolicitud = { asignacionTurnoId: '', tipo: '', descripcion: '' };
      },
      error: (error) => {
        console.error('Error al enviar la solicitud', error);
        alert('Hubo un error al enviar la solicitud');
      }
    });
  }
}
