import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { NavbarUser } from '../../shared/navbar-user/navbar-user';
import { ReportesService } from '../../../domain/services/reportes/reportes';

@Component({
  selector: 'app-reportes-user',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarUser, ButtonModule],
  templateUrl: './reportes-user.html',
  styleUrl: './reportes-user.scss',
})
export class ReportesUser {
  private reportesService = inject(ReportesService);

  // ID del funcionario actual (User 1 para pruebas)
  funcionarioId = 1;

  descargarReportePersonal() {
    // Por ahora usamos el mismo servicio, pero podríamos filtrar por funcionarioId en el futuro
    this.reportesService.descargarReporteGeneral().subscribe({
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
}
