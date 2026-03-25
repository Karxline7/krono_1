import { Component } from '@angular/core';
import { Navbar } from '../../shared/navbar/navbar';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-reportes',
  imports: [CommonModule, FormsModule, Navbar, ButtonModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss',
})
export class Reportes {

}
