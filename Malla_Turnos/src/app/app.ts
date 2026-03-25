import { Component, NgModule, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CartasTurnos } from "./application/components/cartas-turnos/cartas-turnos";
import { Funcionarios } from "./application/components/funcionarios/funcionarios";
import { Horario } from "./application/components/horario/horario";
import { Reportes } from "./application/components/reportes/reportes";
import { Solicitudes } from "./application/components/solicitudes/solicitudes";
import { Login } from "./application/components/login/login";
import { SolicitudesUser } from './application/components/solicitudes-user/solicitudes-user';
import { HorarioUser } from './application/components/horario-user/horario-user';
import { NavbarUser } from './application/shared/navbar-user/navbar-user';




 

@Component({ 
  selector: 'app-root',
  standalone : true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})  


export class App {
  protected readonly title = signal('Malla_Turnos');      
}
