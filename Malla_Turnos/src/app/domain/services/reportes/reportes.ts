import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReportesService {
  private apiUrl = 'http://localhost:8081/api/excel';

  constructor(private http: HttpClient) {}

  descargarReporteGeneral(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/asignaciones`, {
      responseType: 'blob',
    });
  }

  descargarReportePorUsuario(usuarioId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/asignaciones/usuario/${usuarioId}`, {
      responseType: 'blob',
    });
  }
}
