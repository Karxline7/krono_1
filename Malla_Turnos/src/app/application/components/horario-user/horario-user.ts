import { Component, OnInit, OnDestroy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';
import { ButtonModule } from 'primeng/button';
import { ReportesService } from '../../../domain/services/reportes/reportes';
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
  misAsignacionesSemana = signal<DiaAsignacion[]>([]);
  
  // Catálogos cacheados para evitar re-descargar todo cada 5s
  usuariosCache: any[] = [];
  turnosCache: any[] = [];

  constructor(
    private http: HttpClient,
    private reportesService: ReportesService
  ) {}

  descargarReporte() {
    this.reportesService.descargarReportePorUsuario(this.funcionarioId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `mi_reporte_turnos_${new Date().toISOString().split('T')[0]}.xlsx`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      },
      error: (error) => {
        console.error('Error al descargar el reporte personal:', error);
      }
    });
  }

  ngOnInit(): void {
    // 1. Cargamos catálogos una sola vez al inicio
    this.cargarCatalogos();
    
    // 2. Cargamos las asignaciones inmediatamente
    this.cargarAsignaciones();

    // 3. Iniciamos el intervalo de sincronización
    this.syncInterval = setInterval(() => {
      this.refresh();
    }, 5000);
  }

  ngOnDestroy(): void {
    if (this.syncInterval) {
      clearInterval(this.syncInterval);
    }
  }

  cargarCatalogos(): void {
    forkJoin({
      usuarios: this.http.get<any[]>(`${this.baseApiUrl}/usuarios`).pipe(catchError(() => of([]))),
      turnos: this.http.get<any[]>(`${this.baseApiUrl}/turnos`).pipe(catchError(() => of([])))
    }).subscribe({
      next: ({ usuarios, turnos }) => {
        this.usuariosCache = usuarios;
        this.turnosCache = turnos;
        this.procesarInformacionUsuario();
        this.cargarAsignaciones(); // Re-procesamos con la data de catálogos
      }
    });
  }

  procesarInformacionUsuario(): void {
    const funcionario = this.usuariosCache.find(u => Number(u.id) === Number(this.funcionarioId));
    if (funcionario) {
      this.nombreUsuario = funcionario.nombre || 'Usuario Registrado';
      this.cargoUsuario = funcionario.cargoNombre || funcionario.cargo || 'Funcionario';
    } else {
      this.nombreUsuario = 'Usuario no encontrado';
    }
  }

  cargarAsignaciones(): void {
    const fechas = this.generarFechasSemana();
    const nombresDias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];

    this.diasTexto = fechas.map((f, i) => ({
      nombre: nombresDias[i],
      fecha: f.split('-')[2] + '/' + f.split('-')[1]
    }));

    const asignacionesRequests = fechas.map(f =>
      this.http.get<any[]>(`${this.baseApiUrl}/asignaciones/fecha/${f}`).pipe(catchError(() => of([])))
    );

    forkJoin(asignacionesRequests).subscribe({
      next: (asignacionesPorDia) => {
        const turnosMap = new Map<number, any>();
        this.turnosCache.forEach(t => turnosMap.set(Number(t.id), t));

        this.misAsignacionesSemana.set(asignacionesPorDia.map((asignacionesDelDia, index) => {
          const miAsignacion = asignacionesDelDia.find((a: any) => Number(a.funcionarioId) === Number(this.funcionarioId));

          if (miAsignacion) {
            const t = turnosMap.get(Number(miAsignacion.turnoId));
            const esDescanso = t ? t.nombre.toLowerCase().includes('descanso') : true;
            return {
              fecha: fechas[index],
              esDescanso: esDescanso,
              horaInicio: t?.horaInicio || '',
              horaFin: t?.horaFin || '',
              horabreak: t?.horaBreak || t?.horabreak || '',
              horaalmuerzo: t?.horaAlmuerzo || t?.horaalmuerzo || '',
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
        }));
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
    this.cargarAsignaciones();
  }
}
