import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Funcionario {
  id?: number;
  nombre: string;
  tipoDocumento: string;
  numeroDocumento: number | null;
  contrasena?: string | number;
  rolId: number | null;
  cargoId?: number | null;
  cargoNombre?: string;
  areaId: number | null;
}

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  private apiUrl = 'http://localhost:8081/api/usuarios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.apiUrl);
  }

  crear(data: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.apiUrl, data);
  }

  editar(id: number, data: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/${id}`, data);
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}