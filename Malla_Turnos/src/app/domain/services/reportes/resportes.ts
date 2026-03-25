import { Injectable } from '@angular/core';
import { Observable } from '../../../../../node_modules/rxjs/dist/types';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Solicitudes {

  private apiUrl = '/api/tipos-solicitud'; // Cambia esto a tu URL real
  constructor(private http: HttpClient) {}
  usuario: any = {
  };
  
solicitudesAceptar():Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

solicitudesPendientes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

SolicitudesRechazar():Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
}

};

