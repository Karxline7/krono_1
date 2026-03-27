import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../shared/navbar/navbar';
import { FuncionarioService, Funcionario } from '../../../domain/services/Funcionarios/funcionarios';

// PrimeNG
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [
    CommonModule, FormsModule, Navbar, TableModule, 
    ButtonModule, InputTextModule, DialogModule, TagModule, TooltipModule
  ],
  templateUrl: './funcionarios.html',
  styleUrl: './funcionarios.scss',
})
export class Funcionarios implements OnInit, OnDestroy {
  listaFuncionarios: Funcionario[] = [];
  funcionarioActual: Funcionario = this.getInitFuncionario();
  
  mostrarFormulario = false;
  esEdicion = false;
  mostrarModalEliminar = false;
  idAEliminar: number | null = null;

  // Feedback visual (Toast manual)
  verToast = false;
  mensajeToast = '';

  private intervalId: any;

  constructor(private FuncionarioService: FuncionarioService) {}

  ngOnInit(): void {
    this.cargarFuncionarios();

    this.intervalId = setInterval(() => {
      this.refresh();
    }, 5000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  getInitFuncionario(): Funcionario {
    return {
      nombre: '', tipoDocumento: '', numeroDocumento: null,
      contrasena: '', rolId: null, cargoId: null, areaId: null,
    };
  }

  refresh() {
    if (!this.mostrarFormulario && !this.mostrarModalEliminar) {
      this.cargarFuncionarios();
    }
  }

  cargarFuncionarios() {
    this.FuncionarioService.listar().subscribe({
      next: (data) => this.listaFuncionarios = data,
      error: () => this.lanzarToast('Error al cargar datos')
    });
  }

  onGuardar() {
    // Asegurar tipos correctos para el backend
    const dataToSend = {
      ...this.funcionarioActual,
      numeroDocumento: Number(this.funcionarioActual.numeroDocumento),
      rolId: Number(this.funcionarioActual.rolId),
      cargoId: Number(this.funcionarioActual.cargoId)
    };

    if (this.esEdicion && this.funcionarioActual.id) {
      this.FuncionarioService.editar(this.funcionarioActual.id, dataToSend).subscribe({
        next: () => {
          this.lanzarToast("¡Actualizado con éxito!");
          this.cargarFuncionarios();
          this.resetForm();
        },
        error: () => this.lanzarToast("Error al actualizar")
      });
    } else {
      // Valor por defecto para pass si es creación y asegurar que sea Integer (número)
      const pass = this.funcionarioActual.contrasena || '123456';
      dataToSend.contrasena = Number(pass);
      
      this.FuncionarioService.crear(dataToSend).subscribe({
        next: () => {
          this.lanzarToast("¡Guardado con éxito!");
          this.cargarFuncionarios();
          this.resetForm();
        },
        error: () => this.lanzarToast("Error al guardar")
      });
    }
  }

  seleccionarFuncionario(funcionario: Funcionario, accion: 'editar' | 'eliminar') {
    if (accion === 'eliminar') {
      this.idAEliminar = funcionario.id!;
      this.mostrarModalEliminar = true;
    } else {
      this.funcionarioActual = { ...funcionario };
      // Note: Backend returns cargoNombre, and expects cargoId for save
      this.esEdicion = true;
      this.mostrarFormulario = true;
    }
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) {
      this.resetForm();
    }
  }

  eliminarConfirmado() {
    if (this.idAEliminar) {
      this.FuncionarioService.eliminar(this.idAEliminar).subscribe({
        next: () => {
          this.lanzarToast("Funcionario eliminado");
          this.cargarFuncionarios();
          this.cerrarModal();
        }
      });
    }
  }

  cerrarModal() {
    this.mostrarModalEliminar = false;
    this.idAEliminar = null;
  }

  resetForm() {
    this.funcionarioActual = this.getInitFuncionario();
    this.esEdicion = false;
    this.mostrarFormulario = false;
  }

  lanzarToast(msg: string) {
    this.mensajeToast = msg;
    this.verToast = true;
    setTimeout(() => this.verToast = false, 3000);
  }
}