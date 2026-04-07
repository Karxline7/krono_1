import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';
import { ButtonModule } from 'primeng/button';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

interface DiaAsignacion {
  fecha: string;
  esDescanso: boolean;
  horaInicio: string;
  horaFin: string;
  horabreak: string;
  horaalmuerzo: string;
  turnoNombre: string;
}

interface DiaInfo {
  nombre: string;
  fecha: string;
}

@Component({
  selector: 'app-horario-user',
  standalone: true,
  imports: [CommonModule, NavbarUser, ButtonModule],
  templateUrl: './horario-user.html',
  styleUrl: './horario-user.scss',
})
export class HorarioUser implements OnInit, OnDestroy {
  private baseApiUrl = 'http://localhost:8081/api';

  syncInterval: any;

  // El usuario actual para el que se traen los datos (Funcionario ID = 1)
  funcionarioId = 1;

  nombreUsuario = 'Cargando...'; 
  cargoUsuario = 'Funcionario';

  diasTexto: DiaInfo[] = [];
  misAsignacionesSemana: DiaAsignacion[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarDatos();
    this.syncInterval = setInterval(() => {
      this.refresh();
    }, 5000); // Refresca cada 5 segundos para mantener actualizado en el fondo
  }

  ngOnDestroy(): void {
    if (this.syncInterval) {
      clearInterval(this.syncInterval);
    }
  }

  cargarDatos(): void {
    const fechas = this.generarFechasSemana();
    const nombresDias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];

    this.diasTexto = fechas.map((f, i) => ({
      nombre: nombresDias[i],
      fecha: f.split('-')[2] + '/' + f.split('-')[1] // DD/MM visual
    }));

    const asignacionesRequests = fechas.map(f =>
      this.http.get<any[]>(`${this.baseApiUrl}/asignaciones/fecha/${f}`).pipe(catchError(() => of([])))
    );

    forkJoin({
      usuarios: this.http.get<any[]>(`${this.baseApiUrl}/usuarios`).pipe(catchError(() => of([]))),
      turnos: this.http.get<any[]>(`${this.baseApiUrl}/turnos`).pipe(catchError(() => of([]))),
      asignacionesPorDia: forkJoin(asignacionesRequests)
    }).subscribe({
      next: ({ usuarios, turnos, asignacionesPorDia }) => {
        // Buscar el usuario 1 entre todos los usuarios
        const funcionario = usuarios.find(u => Number(u.id) === Number(this.funcionarioId));
        if (funcionario) {
          this.nombreUsuario = funcionario.nombre || 'Usuario Registrado';
          this.cargoUsuario = funcionario.cargoNombre || funcionario.cargo || 'Funcionario';
        } else {
          this.nombreUsuario = 'Usuario no encontrado';
        }

        const turnosMap = new Map<number, any>();
        turnos.forEach(t => turnosMap.set(Number(t.id), t));

        this.misAsignacionesSemana = asignacionesPorDia.map((asignacionesDelDia, index) => {
          // Filtrar rigurosamente por la ID del usuario (Funcionario 1)
          const miAsignacion = asignacionesDelDia.find((a: any) => Number(a.funcionarioId) === Number(this.funcionarioId));

          if (miAsignacion) {
            const t = turnosMap.get(Number(miAsignacion.turnoId));
            const esDescanso = t ? t.nombre.toLowerCase().includes('descanso') : true;
            return {
              fecha: fechas[index],
              esDescanso: esDescanso,
              horaInicio: t?.horaInicio || '',
              horaFin: t?.horaFin || '',
              horabreak: t?.horabreak || '',
              horaalmuerzo: t?.horaalmuerzo || '',
              turnoNombre: t?.nombre || 'Descanso'
            };
          } else {
            return {
              fecha: fechas[index],
              esDescanso: true,
              horaInicio: '',
              horaFin: '',
              horabreak: '',
              horaalmuerzo: '',
              turnoNombre: 'Descanso'
            };
          }
        });
      },
      error: (error) => {
        console.error('Error general al cargar la grilla de turnos', error);
      }
    });
  }

  generarFechasSemana(): string[] {
    const d = new Date();
    const day = d.getDay();
    let diff = 5 - day;
    if (diff < 0) diff += 7;
    d.setDate(d.getDate() + diff); 
    
    // Calcula la semana que cubre al Viernes que marca la fecha base de programación
    const baseDate = new Date(d.toISOString().split('T')[0] + 'T00:00:00');
    const dayOfWeek = baseDate.getDay();
    const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
    
    const monday = new Date(baseDate.getTime());
    monday.setDate(baseDate.getDate() + diffToMonday);

    const fechasSemana: string[] = [];
    for(let i=0; i<7; i++) {
        const dSemana = new Date(monday.getTime());
        dSemana.setDate(monday.getDate() + i);
        fechasSemana.push(dSemana.toISOString().split('T')[0]);
    }
    return fechasSemana;
  }

  refresh() {
    this.cargarDatos();
  }
}
