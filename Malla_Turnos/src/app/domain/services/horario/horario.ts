import { Injectable } from '@angular/core';
import { Observable } from '../../../../../node_modules/rxjs/dist/types';
import { HttpClient } from '@angular/common/http';
import { get } from 'node:http';

@Injectable({
  providedIn: 'root',
})
export class Horario {

  private apiUrl = 'http://localhost:8081/api/asignaciones'; // Cambia esto a tu URL real
  constructor(private http: HttpClient) {}

  listarHorarios(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  
  }

  crearHorario(horario: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, horario);
  }

  editarHorario(id: number, horario: any): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<any>(url, horario);
  }

  eliminarHorario(id: number): Observable<any> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<any>(url);
  }

    
  
}
