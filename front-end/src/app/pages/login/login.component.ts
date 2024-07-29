import { Component, OnInit } from '@angular/core';
import { User } from './model/User';
import { LoginService } from '../../services/login.service';
import { NgForm } from '@angular/forms';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loading = false;
  submitted = false;
  user!:User;
  singup:boolean=false;

  getClassObject() {
    return {
      'right-panel-active': this.singup,
    };
  }
  annimationSingUp(){
    this.singup = !this.singup;
  }
  constructor(private loginservice:LoginService ,private router:Router) { }
  ngOnInit(): void {
   
  this.user= new User();
  }
  
  onSubmit(f:NgForm) {
    this.submitted = true;
    this.loading = true;
    
    this.loginservice.postLogin(this.user).subscribe(
      (res: any) => {
        if (res.result) {
          //alert('Login successful');
          localStorage.setItem('loginToken', res.accessToken);
          this.router.navigateByUrl('/home');
        } else {
          alert("une erreur s'est produite lors de la connexion");
        }
        this.loading = false;
      },
      (error) => {
        alert('Login failed');
        console.log(error);
        this.loading = false;
      }
    );
    this.loading=false;
  }
 save(f:NgForm){
    
  }
  
  
}
