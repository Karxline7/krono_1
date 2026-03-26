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

  // Emulando el usuario actual que inició sesión (Funcionario ID = 1)
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
      funcionario: this.http.get<any>(`${this.baseApiUrl}/funcionarios/${this.funcionarioId}`).pipe(catchError(() => of(null))),
      turnos: this.http.get<any[]>(`${this.baseApiUrl}/turnos`).pipe(catchError(() => of([]))),
      asignacionesPorDia: forkJoin(asignacionesRequests)
    }).subscribe({
      next: ({ funcionario, turnos, asignacionesPorDia }) => {
        if (funcionario) {
          this.nombreUsuario = funcionario.nombre || 'Usuario Registrado';
        }

        const turnosMap = new Map<number, any>();
        turnos.forEach(t => turnosMap.set(t.id, t));

        this.misAsignacionesSemana = asignacionesPorDia.map((asignacionesDelDia, index) => {
          // Filtrar rigurosamente SOLO POR LA ID DEL USUARIO ACTIVO (nadie más que él)
          const miAsignacion = asignacionesDelDia.find((a: any) => a.funcionarioId === this.funcionarioId);

          if (miAsignacion) {
            const t = turnosMap.get(miAsignacion.turnoId);
            const esDescanso = t ? t.nombre === 'Descanso' : true;
            return {
              fecha: fechas[index],
              esDescanso: esDescanso,
              horaInicio: t?.horainicio || '',
              horaFin: t?.horafin || '',
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
