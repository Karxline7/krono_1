import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Para *ngIf y *ngFor
import { FormsModule } from '@angular/forms';   // Para [(ngModel)]
import { TurnoService, Turno } from '../../../domain/services/cartas-turnos/cartas-turnos';
import { Navbar } from "../../shared/navbar/navbar";
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { RippleModule } from 'primeng/ripple';
import { DialogModule } from 'primeng/dialog';


@Component({
  selector: 'app-cartas-turnos',
  standalone: true, // Asegúrate de que esto esté presente si no usas NgModules
  imports: [CommonModule, FormsModule, Navbar, DialogModule, TableModule, ButtonModule, InputTextModule, InputNumberModule, CardModule, TagModule, TooltipModule, RippleModule],
  templateUrl: './cartas-turnos.html',
  styleUrls: ['./cartas-turnos.scss']
})
export class CartasTurnos implements OnInit {
  turnos: Turno[] = [];
  accionActual: 'agregar' | 'editar' | null = null;
  turnoSeleccionado: Turno | null = null;
  
  nuevoTurno: Turno = {
    nombre: '',
    horaInicio: '',
    horaFin: '',
    horaalmuerzo: '',
    horabreak: '',
    tipo: '',
    cantidadPersonas: 0 
  };

  constructor(private turnoService: TurnoService) {}

  refresh() {
    this.cargarTurnos();
  }

  ngOnInit() {
    this.cargarTurnos();
  }

  cargarTurnos() {
    this.turnoService.listar().subscribe({
      next: (data: any[]) => {
        this.turnos = data.map(t => ({
          ...t, 
          cantidadPersonas: t.cantidadPersonas || Math.floor(Math.random() * 10) + 1
        }));
      },
      error: (err) => console.error('Error al cargar los turnos:', err)
    });
  }

  get statsDinamicas() {
    const resumen = this.turnos.reduce((acc: any, t) => {
      const personas = t.cantidadPersonas || 0;
      acc[t.tipo] = (acc[t.tipo] || 0) + personas;
      return acc;
    }, {});
    
    return Object.keys(resumen).map(key => ({
      nombre: key,
      cantidad: resumen[key]
    }));
  }

  onGuardar() {
    this.turnoService.crear(this.nuevoTurno).subscribe(() => {
      this.cargarTurnos();
      this.resetForms();
    });
  }

  resetForms() {
    this.accionActual = null;
    this.turnoSeleccionado = null;
    this.nuevoTurno = {
      nombre: '', horaInicio: '', horaFin: '', 
      horaalmuerzo: '', horabreak: '', tipo: '', cantidadPersonas: 0 
    };
  }

  onActualizar() {
    if (this.turnoSeleccionado?.id) {
      this.turnoService.editar(this.turnoSeleccionado.id, this.turnoSeleccionado).subscribe(() => {
        this.cargarTurnos();
        this.resetForms();
      });
    }
  }

  seleccionarTurno(turno: Turno, accion: 'editar' | 'eliminar') {
    if (accion === 'eliminar' && turno.id) {
      this.turnoService.eliminar(turno.id).subscribe(() => this.cargarTurnos());
    } else {
      this.turnoSeleccionado = { ...turno };
      this.accionActual = 'editar';
    }
  }
}