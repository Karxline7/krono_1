import { Component, inject } from '@angular/core';
import { Navbar } from '../../shared/navbar/navbar';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { ReportesService } from '../../../domain/services/reportes/reportes';

@Component({
  selector: 'app-reportes',
  imports: [CommonModule, FormsModule, Navbar, ButtonModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss',
})
export class Reportes {
  private reportesService = inject(ReportesService);

  descargarReporte() {
    this.reportesService.descargarReporteGeneral().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_asignaciones.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      },
      error: (error) => {
        console.error('Error al descargar el reporte:', error);
        // Podrías añadir un toast de PrimeNG aquí si lo consideras necesario
      }
    });
  }
}
