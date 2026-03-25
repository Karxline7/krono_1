import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
 
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {
 
  tipo_documento: string = '';
  usuario: string = '';
  contrasena: string = '';
  aceptaPoliticas: boolean = false;
 
  mensaje: string = '';
  tipoMensaje: 'error' | 'warning' = 'error';
 
  constructor(private router: Router) {}
 
  iniciarSesion() {
 
    if (this.tipo_documento && this.usuario && this.contrasena && this.aceptaPoliticas) {
 
      if (this.usuario === 'admin' && this.contrasena === 'admin') {
        this.mensaje = '';
        this.router.navigate(['/cartas-turnos']);
 
      } else if (this.usuario === 'user' && this.contrasena === 'user') {
        this.mensaje = '';
        this.router.navigate(['/horario-user']);
 
      } else {
        this.mensaje = 'Usuario o contraseña incorrectos.';
        this.tipoMensaje = 'error';
        this.ocultarMensaje();
      }
 
    } else {
      this.mensaje = 'Por favor, completa todos los campos y acepta las políticas.';
      this.tipoMensaje = 'warning';
      this.ocultarMensaje();
    }
  }
 
  ocultarMensaje() {
    setTimeout(() => {
      this.mensaje = '';
    }, 3000);
  }
}