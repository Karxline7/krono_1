import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Turno {
  id?: number;
  nombre: string;       // Coincide con Java
  horaInicio: string;
  horaFin: string;
  horaalmuerzo: string; // Coincide con Java request.getHoraalmuerzo()
  horabreak: string;   // Coincide con Java request.getHorabreak()
  tipo: string;         // Para tus estadísticas
  cantidadPersonas?: number; // Para el contador que pediste
}

@Injectable({providedIn: 'root'})
export class TurnoService {
  private apiUrl = 'http://localhost:8081/api/turnos'; // Ajusta tu puerto

  constructor(private http: HttpClient) {}

  listar(): Observable<Turno[]> {
    return this.http.get<Turno[]>(this.apiUrl);
  }

  obtener(id: number): Observable<Turno> {
    return this.http.get<Turno>(`${this.apiUrl}/${id}`);
  }

  crear(turno: Turno): Observable<Turno> {
    const payload = {
      ...turno,
      horaAlmuerzo: turno.horaalmuerzo,
      horaBreak: turno.horabreak
    };
    return this.http.post<Turno>(this.apiUrl, payload);
  }

  editar(id: number, turno: Turno): Observable<Turno> {
    return this.http.put<Turno>(`${this.apiUrl}/${id}`, turno);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}