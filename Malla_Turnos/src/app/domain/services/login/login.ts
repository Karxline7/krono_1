import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from '../../../../../node_modules/rxjs/dist/types';


@Injectable({
  providedIn: 'root',
})
export class Login {

  private apiUrl = 'http://localhost:8090/api/login';
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const url = `${this.apiUrl}/login`;
    return this.http.post<any>(url, { username, password });
  }

} 
  
