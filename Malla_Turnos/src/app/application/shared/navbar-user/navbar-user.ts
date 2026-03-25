import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar-user',
  standalone: true,   
  imports: [CommonModule, RouterModule],
        templateUrl: './navbar-user.html',
  styleUrl: './navbar-user.scss',
})
export class NavbarUser {

  userName: string = 'Felipe Rodriguez';
  userRole: string = 'User';
 
  menuAbierto: boolean = false;
 
  toggleMenu(){
    this.menuAbierto = !this.menuAbierto;
  }
 
  cerrarMenu(){
    this.menuAbierto = false;
  }

}
