import { Component ,OnInit} from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RoleGuard } from '../../services/role-gard.service';

@Component({
  selector: 'app-dashbord-admin',
  templateUrl: './dashbord-admin.component.html',
  styleUrl: './dashbord-admin.component.css'
})
export class DashbordAdminComponent implements OnInit{
  ngOnInit(): void {this.rolegard.role = 'admin';}
  constructor(private loginser:LoginService,private rolegard:RoleGuard) { }
  logout() {
    this.loginser.logout();
  }
  

}
