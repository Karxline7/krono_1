import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';

@Component({
  selector: 'app-horario-user',
  standalone: true,
  imports: [CommonModule, NavbarUser],
  templateUrl: './horario-user.html',
  styleUrl: './horario-user.scss',
})
export class HorarioUser implements OnInit {
  // Ajusta la URL a la ruta de tu API para consultar los turnos de un usuario en específico
  private apiUrl = '/api/turnos/mis-turnos';

  nombreUsuario = 'Usuario Actual'; // Este valor luego vendrá del inicio de sesión (Login)
  cargoUsuario = 'Funcionario';

  misTurnos: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarMisTurnos();
  }

  cargarMisTurnos(): void {
    // Si necesitas enviarle el nombre del usuario o su ID, puedes hacerlo por query parameters: 
    // this.http.get(`${this.apiUrl}?nombre=${this.nombreUsuario}`)
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.misTurnos = data;
        console.log('Mis turnos cargados:', data);
      },
      error: (error) => {
        console.error('Error al cargar mis turnos', error);
      }
    });
  }
}
