import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-solicitudes-user',
  standalone: true,   
  imports: [CommonModule, FormsModule, NavbarUser, ButtonModule],
  templateUrl: './solicitudes-user.html',
  styleUrl: './solicitudes-user.scss',
})
export class SolicitudesUser {
  private apiUrl = '/api/solicitudes';

  nuevaSolicitud = {
    tipo: '',
    descripcion: ''
  };

  constructor(private http: HttpClient) {}

  enviarSolicitud() {
    if (!this.nuevaSolicitud.tipo || !this.nuevaSolicitud.descripcion) {
      alert('Por favor llene todos los campos');
      return;
    }

    const payload = {
      titulo: this.nuevaSolicitud.tipo === '1' ? 'Cambio de turno' : 'Eliminacion de turno',
      mensaje: this.nuevaSolicitud.descripcion,
      nombre: 'Usuario Solicitante',
      cargo: 'Funcionario',
      estado: 'Pendiente'
    };

    this.http.post(this.apiUrl, payload).subscribe({
      next: () => {
        alert('Solicitud enviada correctamente');
        this.nuevaSolicitud = { tipo: '', descripcion: '' };
      },
      error: (error) => {
        console.error('Error al enviar la solicitud', error);
        alert('Hubo un error al enviar la solicitud');
      }
    });
  }
}
