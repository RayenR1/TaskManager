import { Component, OnInit } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { crudUser } from './model/crudUser';
import { AddUserService } from '../../../services/adminServices/add-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.css'
})
export class AddUserComponent implements OnInit {
  form!: crudUser;
  valid!: boolean;
  admin: boolean=false;
  director: boolean=false;
  employee: boolean=false;
  
  password1!: string;
  password2!: string;
  constructor(private addUserService: AddUserService,private router:Router) { }

  ngOnInit(): void {
    this.form = new crudUser(); 
    
  }
  

  getClassObject(f: NgModel) {
    if(f.touched){return {
      'is-valid': f.valid,'is-invalid':!f.valid
    };}
    return {};
    
  }
  get isAnyRoleSelected(): boolean {
    return this.admin || this.director || this.employee;
  }
  get equalpassword(): boolean {
    return this.password1 === this.password2;
  }
  confirmPasswordClassObject(f: NgModel) {
    if(f.touched){return {
      'is-valid': this.equalpassword,'is-invalid':!this.equalpassword
    };}
    return {};
    
  }
  oncheckAdmin(){
    const index = this.form.roles.indexOf('admin');
    if(this.admin&&index === -1){
      this.form.roles.push('admin');
    }else if(!this.admin&&index !== -1){
      this.form.roles.splice(index, 1);
    }
    
  }
  oncheckdirector(){
    const index = this.form.roles.indexOf('director');
    if(this.director&&index === -1){
      this.form.roles.push('director');
    }else if(!this.director&&index !== -1){
      this.form.roles.splice(index, 1);
    }
  }
  oncheckEmployee(){
    const index = this.form.roles.indexOf('employee');
    if(this.employee&&index === -1){
      this.form.roles.push('employee');
    }else if(!this.employee&&index !== -1){
      this.form.roles.splice(index, 1);
    }
  }
  onsubmit(f: NgForm){ 
    if(this.equalpassword){this.form.password=this.password1;}
    this.addUserService.registerUser(this.form)
            .subscribe(
              (res: any) => {
                if (res.result) {
                  alert('Add successfuly');
                  this.form = {
                    firstName: '',
                    lastName: '',
                    team:'',
                    email:'',
                    password:'',
                    roles:[]    
                  }
                  this.router.navigateByUrl('/dashbordAdmin/listusers');
                } else {
                  alert(" erreur ");
                }
                
              },
              (error) => {
                alert("The information is invalid");
              }
            ); 
  }

  
  
}
