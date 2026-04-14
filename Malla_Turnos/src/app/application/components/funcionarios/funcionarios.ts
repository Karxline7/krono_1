import { Component, OnInit, OnDestroy, ViewChild, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Navbar } from '../../shared/navbar/navbar';
import { Observable } from 'rxjs';
import { FuncionarioService, Funcionario } from '../../../domain/services/Funcionarios/funcionarios';

// PrimeNG
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [
    CommonModule, FormsModule, Navbar,
    ButtonModule, InputTextModule, DialogModule
  ],
  templateUrl: './funcionarios.html',
  styleUrl: './funcionarios.scss',
})
export class Funcionarios implements OnInit, OnDestroy {
  @ViewChild('funcionarioForm') funcionarioForm!: NgForm;

  listaFuncionarios: Funcionario[] = [];
  funcionarioActual: Funcionario = this.getInitFuncionario();

  mostrarFormulario = false;
  esEdicion = false;

  mostrarModalEliminar = false;

  idAEliminar: number | null = null;

  verToast = false;
  mensajeToast = '';

  procesando = false;
  private intervalId: any;

  constructor(private funcionarioService: FuncionarioService) {}

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
    if (!this.mostrarFormulario && !this.mostrarModalEliminar && !this.procesando) {
      this.cargarFuncionarios(true);
    }
  }

  cargarFuncionarios(silencioso = false) {
    this.funcionarioService.listar().subscribe({
      next: (data) => this.listaFuncionarios = [...data],
      error: () => {
        if (!silencioso) {
          this.lanzarToast('Error al cargar datos');
        }
      }
    });
  }

  // 🔥 BOTÓN GUARDAR
  onGuardar() {
    if (this.procesando) return;

    if (this.esEdicion) {
      this.confirmarEdicion();
    } else {
      this.confirmarCreacion();
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

    this.funcionarioService.crear(dataToSend).subscribe({
      next: () => {
        this.procesando = false;
        this.resetForm();
        this.cargarFuncionarios(true);
        this.lanzarToast("Funcionario creado");
      },
      error: () => {
        this.procesando = false;
        this.lanzarToast("Error al crear");
      }
    });
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

    this.funcionarioService.editar(this.funcionarioActual.id, dataToSend).subscribe({
      next: () => {
        this.procesando = false;
        this.resetForm();
        this.cargarFuncionarios(true);
        this.lanzarToast("Funcionario actualizado");
      },
      error: () => {
        this.procesando = false;
        this.lanzarToast("Error al actualizar");
      }
    });
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
    if (this.idAEliminar !== null) {
      this.procesando = true;
      this.funcionarioService.eliminar(this.idAEliminar).subscribe({
        next: () => {
          this.procesando = false;
          this.cerrarModalEliminar();
          this.cargarFuncionarios(true);
          this.lanzarToast("Funcionario eliminado");
        },
        error: () => {
          this.procesando = false;
          this.lanzarToast("Error al eliminar");
        }
      });
    }
  }

  cerrarModalEliminar() {
    this.mostrarModalEliminar = false;
    this.idAEliminar = null;
  }

  cerrarModal() {
    this.cerrarModalEliminar();
  }

  toggleFormulario() {
    this.mostrarFormulario = !this.mostrarFormulario;
    if (!this.mostrarFormulario) {
      this.resetForm();
    }
  }

  resetForm() {
    this.esEdicion = false;
    this.mostrarFormulario = false;
    if (this.funcionarioForm) {
      this.funcionarioForm.resetForm(this.getInitFuncionario());
    } else {
      this.funcionarioActual = this.getInitFuncionario();
    }
  }

  lanzarToast(msg: string) {
    this.verToast = false;
    this.mensajeToast = msg;
    
    // Un retraso mínimo para permitir que el DOM detecte el cambio de false -> true
    // y dispare la animación CSS.
    setTimeout(() => {
      this.verToast = true;
    }, 10);

    // Auto-ocultar después de 3 segundos
    setTimeout(() => {
      if (this.mensajeToast === msg) {
        this.verToast = false;
      }
    }, 3000);
  }
}