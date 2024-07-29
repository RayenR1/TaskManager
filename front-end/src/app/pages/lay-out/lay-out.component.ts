import { Component,OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { RoleGuard } from '../../services/role-gard.service';
import { NgForm, NgModel ,FormsModule} from '@angular/forms';
import { RolesService } from '../../services/adminServices/roles.service';


@Component({
  selector: 'app-lay-out',
  templateUrl: './lay-out.component.html',
  styleUrl: './lay-out.component.css'
})
export class LayOutComponent implements OnInit{
  Admin:boolean=true;
  Director:boolean=true;
  Employee:boolean=true;

  constructor(private loginser:LoginService,private rolegard:RoleGuard,private role:RolesService) { }
  logout() {
    this.loginser.logout();
  }
  ngOnInit() {
    this.role.checkRoleWithAPI().subscribe((data:any)=>{
      if(data.role.includes('admin')){
        this.Admin = false;
      } 
      if(data.role.includes('director')){
        this.Director = false;
      } 
      if(data.role.includes('employee')){
        this.Employee = false;
      }
    });
    
  }
  admin() {this.rolegard.role = 'admin';}
  director() {this.rolegard.role = 'director';}
  employee() {this.rolegard.role = 'employee';}
  


}
