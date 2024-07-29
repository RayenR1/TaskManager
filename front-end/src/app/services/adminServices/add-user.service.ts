import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { crudUser } from '../../pages/dashbord-admin/add-user/model/crudUser';
import { TestRequest } from '@angular/common/http/testing';

@Injectable({
  providedIn: 'root'
})
export class AddUserService {
   apiurl:string = 'http://localhost:9090/api/users/create';
  constructor(private http: HttpClient) {}
  splitStringIntoWords(input: string): string[] {
    // Utilisation d'une expression régulière pour séparer par des caractères non alphabétiques ou numériques
    const words = input.split(/[^a-zA-Z0-9]+/).filter(word => word.trim() !== '');
    return words; 
  }

  registerUser(user:crudUser ): Observable<any> {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const equipes=this.splitStringIntoWords(user.team);

      const addRequest={
        email: user.email,
        password: user.password,
        nom: user.firstName,
        prenom: user.lastName,
        societe:'' ,
        roles: user.roles,
        taches: [],
        equipes: equipes,
        accessToken: localStorage.getItem('loginToken')
    }
     

    return this.http.post<any>(this.apiurl, addRequest, { headers });
  }
}
