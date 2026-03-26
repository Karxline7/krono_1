import { Component, OnInit, OnDestroy } from '@angular/core';
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
export class Solicitudes implements OnInit, OnDestroy {
  
  private apiUrl = 'http://localhost:8081/api/solicitudes';
  private tiposUrl = 'http://localhost:8081/api/tipos-solicitud';

  syncInterval: any;

  solicitudes: SolicitudUI[] = [];
  solicitudSeleccionada: SolicitudUI | null = null;
  tiposSolicitud: any[] = [];

procesandoAccion: boolean = false;
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarDatos();
    this.syncInterval = setInterval(() => {
      this.refresh();
    }, 3000);
  }

  ngOnDestroy(): void {
    if (this.syncInterval) {
      clearInterval(this.syncInterval);
    }
  }
  refresh() {
    // Solo refrescamos si no estamos procesando un botón de aceptar/rechazar
    if (!this.procesandoAccion) {
      if (this.tiposSolicitud.length > 0) {
        this.cargarSolicitudes();
      } else {
        this.cargarDatos();
      }
    }
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
        // Guardamos el ID de la solicitud que el usuario tenía seleccionada antes del refresh
        const idSeleccionadoPreviamente = this.solicitudSeleccionada?.id;

        this.solicitudes = data.map(solicitud => {
          const tipo = this.tiposSolicitud.find(t => t.id === solicitud.tipoSolicitudId);
          return {
            ...solicitud,
            nombreTipoSolicitud: tipo ? tipo.nombre : 'Tipo ' + solicitud.tipoSolicitudId
          };
        });

        // Lógica de Selección Inteligente:
        if (this.solicitudes.length > 0) {
          // 1. Intentamos buscar la solicitud que ya estaba seleccionada para no perder el foco
          const seleccionadaAunExiste = this.solicitudes.find(s => s.id === idSeleccionadoPreviamente);
          
          if (seleccionadaAunExiste) {
            this.solicitudSeleccionada = seleccionadaAunExiste;
          } else {
            // 2. Si ya no existe (porque otro la aprobó/rechazó), volvemos a la primera
            this.solicitudSeleccionada = this.solicitudes[0];
          }
        } else {
          this.solicitudSeleccionada = null;
        }
      },
      error: (error) => console.error('Error cargando solicitudes:', error)
    });
  }

  seleccionarSolicitud(s: SolicitudUI): void {
    this.solicitudSeleccionada = s;
  }

  aceptar(): void {
    if (this.solicitudSeleccionada) {
      this.procesandoAccion = true; // Bloqueamos el refresh automático momentáneamente
      this.http.put(`${this.apiUrl}/${this.solicitudSeleccionada.id}/aprobar`, {}).subscribe({
        next: () => {
          alert('Solicitud Aprobada');
          this.procesandoAccion = false;
          this.cargarDatos();
        },
        error: (error) => {
          console.error('Error al aprobar:', error);
          this.procesandoAccion = false;
          alert('Hubo un error al aprobar la solicitud.');
        }
      });
    }
  }

 rechazar(): void {
    if (this.solicitudSeleccionada) {
      this.procesandoAccion = true; // Bloqueamos el refresh automático
      this.http.put(`${this.apiUrl}/${this.solicitudSeleccionada.id}/denegar`, {}).subscribe({
        next: () => {
          alert('Solicitud Denegada');
          this.procesandoAccion = false;
          this.cargarDatos();
        },
        error: (error) => {
          console.error('Error al denegar:', error);
          this.procesandoAccion = false;
          alert('Hubo un error al denegar la solicitud.');
        }
      });
    }
  }
}