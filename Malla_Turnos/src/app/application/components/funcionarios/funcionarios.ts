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
  mostrarModalEditar = false;
  mostrarModalCrear = false;

  idAEliminar: number | null = null;

  verToast = false;
  mensajeToast = '';

  procesando = false;
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
      nombre: '',
      tipoDocumento: '',
      numeroDocumento: null,
      contrasena: '',
      rolId: null,
      cargoId: null,
      areaId: null,
    };
  }

  refresh() {
    if (!this.mostrarFormulario && !this.mostrarModalEliminar && !this.mostrarModalEditar && !this.mostrarModalCrear && !this.procesando) {
      this.cargarFuncionarios();
    }
  }

  cargarFuncionarios() {
    this.FuncionarioService.listar().subscribe({
      next: (data) => this.listaFuncionarios = [...data],
      error: () => this.lanzarToast('Error al cargar datos')
    });
  }

  // 🔥 BOTÓN GUARDAR
  onGuardar() {
    if (this.procesando) return;

    if (this.esEdicion) {
      this.mostrarModalEditar = true;
    } else {
      this.mostrarModalCrear = true;
    }
  }

  // 🟢 CONFIRMAR CREAR
  confirmarCreacion() {
    this.procesando = true;

    const dataToSend = {
      ...this.funcionarioActual,
      numeroDocumento: Number(this.funcionarioActual.numeroDocumento),
      rolId: Number(this.funcionarioActual.rolId),
      cargoId: Number(this.funcionarioActual.cargoId),
      areaId: Number(this.funcionarioActual.areaId),
      contrasena: (this.funcionarioActual.contrasena || '123456').toString()
    };

    this.FuncionarioService.crear(dataToSend).subscribe({
      next: () => {
        this.procesando = false;
        this.lanzarToast("Funcionario creado");
        this.cargarFuncionarios();
        this.cerrarModalCrear();
        this.resetForm();
      },
      error: () => {
        this.procesando = false;
        this.lanzarToast("Error al crear");
      }
    });
  }

  cerrarModalCrear() {
    this.mostrarModalCrear = false;
  }

  // 🟡 CONFIRMAR EDITAR
  confirmarEdicion() {
    if (!this.funcionarioActual.id) return;

    this.procesando = true;

    const dataToSend = {
      ...this.funcionarioActual,
      numeroDocumento: Number(this.funcionarioActual.numeroDocumento),
      rolId: Number(this.funcionarioActual.rolId),
      cargoId: Number(this.funcionarioActual.cargoId),
      areaId: Number(this.funcionarioActual.areaId)
    };

    this.FuncionarioService.editar(this.funcionarioActual.id, dataToSend).subscribe({
      next: () => {
        this.procesando = false;
        this.lanzarToast("Funcionario actualizado");
        this.cargarFuncionarios();
        this.cerrarModalEditar();
        this.resetForm();
      },
      error: () => {
        this.procesando = false;
        this.lanzarToast("Error al actualizar");
      }
    });
  }

  cerrarModalEditar() {
    this.mostrarModalEditar = false;
  }

  seleccionarFuncionario(funcionario: Funcionario, accion: 'editar' | 'eliminar') {
    if (accion === 'eliminar') {
      this.idAEliminar = funcionario.id!;
      this.mostrarModalEliminar = true;
    } else {
      this.funcionarioActual = { ...funcionario };
      this.esEdicion = true;
      this.mostrarFormulario = true;
    }
  }

  eliminarConfirmado() {
    if (this.idAEliminar) {
      this.FuncionarioService.eliminar(this.idAEliminar).subscribe({
        next: () => {
          this.lanzarToast("Funcionario eliminado");
          this.cargarFuncionarios();
          this.cerrarModalEliminar();
        },
        error: () => {
          this.lanzarToast("Error al eliminar");
        }
      });
    }
  }

  cerrarModalEliminar() {
    this.mostrarModalEliminar = false;
    this.idAEliminar = null;
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) {
      this.resetForm();
    }
  }

  resetForm() {
    this.funcionarioActual = this.getInitFuncionario();
    this.esEdicion = false;
    this.mostrarFormulario = false;
  }

  lanzarToast(msg: string) {
    this.verToast = false;

    setTimeout(() => {
      this.mensajeToast = msg;
      this.verToast = true;

      setTimeout(() => this.verToast = false, 3000);
    }, 50);
  }
}