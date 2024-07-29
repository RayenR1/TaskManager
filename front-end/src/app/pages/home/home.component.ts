import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RoleGuard } from '../../services/role-gard.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private loginser:LoginService,private rolegard:RoleGuard) { }
  logout() {
    this.loginser.logout();
  }
  admin() {this.rolegard.role = 'admin';}
  director() {this.rolegard.role = 'director';}
  employee() {this.rolegard.role = 'employee';}

}
