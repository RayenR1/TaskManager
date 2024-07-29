import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../pages/login/model/User';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class LoginService {
  apiurl: string='http://localhost:9090/api/auth/login';

  constructor(private http:HttpClient,private router:Router) { }
  postLogin(user:User){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const loginRequest={
      email:user.Email,
      password:user.password
    }
    return this.http.post<any>(this.apiurl, loginRequest, { headers });
  }
  logout() {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http.post<any>('http://localhost:9090/api/auth/logout', { "accessToken": `${localStorage.getItem('loginToken')}` }, { headers }).subscribe(
      response => {
        localStorage.removeItem('loginToken');
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Error logging out:', error);
        // Handle error appropriately, maybe show a message to the user
      }
    );
  }
  
  isLoggedIn(): Observable<boolean> {
    return of(!!localStorage.getItem('loginToken'));
  }
  
}
 