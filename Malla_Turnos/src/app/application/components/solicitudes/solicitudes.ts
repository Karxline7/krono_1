import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../shared/navbar/navbar';
import { ButtonModule } from 'primeng/button';

export interface SolicitudTurnoDto {
  id: number;
  asignacionTurnoId: number;
  tipoSolicitudId: number;
  motivoSolicitud: string;
  estado: string;
}

export interface SolicitudUI extends SolicitudTurnoDto {
  nombreTipoSolicitud?: string;       // Extraido del controlador de Tipos
}

@Component({
  selector: 'app-solicitudes',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar, ButtonModule],
  templateUrl: './solicitudes.html',
  styleUrl: './solicitudes.scss',
})
export class Solicitudes implements OnInit {
  
  private apiUrl = 'http://localhost:8081/api/solicitudes';
  private tiposUrl = 'http://localhost:8081/api/tipos-solicitud';

  solicitudes: SolicitudUI[] = [];
  solicitudSeleccionada: SolicitudUI | null = null;
  tiposSolicitud: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    // Primero cargamos los tipos de solicitud para poder mapear sus nombres
    this.http.get<any[]>(this.tiposUrl).subscribe({
      next: (tipos) => {
        this.tiposSolicitud = tipos;
        this.cargarSolicitudes();
      },
      error: (e) => {
        console.error('Error cargando tipos de solicitud:', e);
        this.cargarSolicitudes(); // Intentamos cargar las solicitudes de todos modos
      }
    });
  }

  cargarSolicitudes() {
    this.http.get<SolicitudUI[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.solicitudes = data.map(solicitud => {
           const tipo = this.tiposSolicitud.find(t => t.id === solicitud.tipoSolicitudId);
           return {
             ...solicitud,
             nombreTipoSolicitud: tipo ? tipo.nombre : 'Tipo ' + solicitud.tipoSolicitudId
           };
        });

        if (this.solicitudes.length > 0) {
          this.solicitudSeleccionada = this.solicitudes[0];
        } else {
          this.solicitudSeleccionada = null;
        }
      },
      error: (error) => {
        console.error('Error cargando solicitudes:', error);
      }
    });
  }

  seleccionarSolicitud(s: SolicitudUI): void {
    this.solicitudSeleccionada = s;
  }

  aceptar(): void {
    if (this.solicitudSeleccionada) {
      // Usa el endpoint que espera el backend para aprobar
      this.http.put(`${this.apiUrl}/${this.solicitudSeleccionada.id}/aprobar`, {}).subscribe({
        next: () => {
          alert('Solicitud Aprobada');
          this.cargarDatos(); // Recargar la lista
        },
        error: (error) => {
          console.error('Error al aprobar:', error);
          alert('Hubo un error al aprobar la solicitud.');
        }
      });
    }
  }

  rechazar(): void {
    if (this.solicitudSeleccionada) {
      // Usa el endpoint que espera el backend para denegar
      this.http.put(`${this.apiUrl}/${this.solicitudSeleccionada.id}/denegar`, {}).subscribe({
        next: () => {
          alert('Solicitud Denegada');
          this.cargarDatos(); // Recargar la lista
        },
        error: (error) => {
          console.error('Error al denegar:', error);
          alert('Hubo un error al denegar la solicitud.');
        }
      });
    }
  }
}