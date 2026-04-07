import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FuncionarioService, Funcionario } from '../../../domain/services/Funcionarios/funcionarios';

@Component({
  selector: 'app-navbar-user',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar-user.html',
  styleUrl: './navbar-user.scss',
})
export class NavbarUser implements OnInit {

  userName: string = 'Cargando...';
  userRole: string = 'Usuario';
  funcionarioId = 1; // ID por defecto para el usuario actual
 
  menuAbierto: boolean = false;

  constructor(private funcionarioService: FuncionarioService) {}

  ngOnInit(): void {
    this.cargarDatosUsuario();
  }

  cargarDatosUsuario() {
    this.funcionarioService.listar().subscribe({
      next: (usuarios: Funcionario[]) => {
        const usuarioActual = usuarios.find(u => Number(u.id) === this.funcionarioId);
        if (usuarioActual) {
          this.userName = usuarioActual.nombre;
          this.userRole = usuarioActual.cargoNombre || 'Funcionario';
        } else {
          this.userName = 'Usuario no encontrado';
        }
      },
      error: (error: any) => {
        console.error('Error al cargar datos del usuario en navbar:', error);
        this.userName = 'Error de conexión';
      }
    });
  }
 
  toggleMenu(){
    this.menuAbierto = !this.menuAbierto;
  }
 
  cerrarMenu(){
    this.menuAbierto = false;
  }

}
