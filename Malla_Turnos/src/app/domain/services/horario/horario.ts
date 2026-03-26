import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Asignacion {
  id?: number;
  funcionarioId: number;
  turnoId: number;
  fecha: string;
}

@Injectable({
  providedIn: 'root',
})
export class AsignacionService {

  private apiUrl = 'http://localhost:8081/api/asignaciones';
  
  constructor(private http: HttpClient) {}

  listar(): Observable<Asignacion[]> {
    return this.http.get<Asignacion[]>(this.apiUrl);
  }

  listarPorFecha(fecha: string): Observable<Asignacion[]> {
    return this.http.get<Asignacion[]>(`${this.apiUrl}/fecha/${fecha}`);
  }

  crear(data: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, data);
  }

  editar(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, data);
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
