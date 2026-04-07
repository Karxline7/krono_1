import { Routes } from '@angular/router';
import { Login } from './application/components/login/login';
import { Solicitudes } from './application/components/solicitudes/solicitudes';
import { CartasTurnos } from './application/components/cartas-turnos/cartas-turnos';
import { HorarioUser } from './application/components/horario-user/horario-user';
import { SolicitudesUser } from './application/components/solicitudes-user/solicitudes-user';
import { Horario } from './application/components/horario/horario';
import { Funcionarios } from './application/components/funcionarios/funcionarios';
import { Reportes } from './application/components/reportes/reportes';
import { ReportesUser } from './application/components/reportes-user/reportes-user';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: Login },
    // Admin routes
    { path: 'solicitudes', component: Solicitudes },
    { path: 'cartas-turnos', component: CartasTurnos },
    { path: 'horario', component: Horario },
    { path: 'funcionarios', component : Funcionarios },
    // User routes
    { path: 'solicitudes-user', component: SolicitudesUser },
    { path: 'horario-user', component: HorarioUser }
];