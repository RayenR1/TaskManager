import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UpdateUserService {

  
  apiurl:string = 'http://localhost:9090/api/users/update-';
  constructor(private http: HttpClient) {}
  splitStringIntoWords(input: string): string[] {
    // Utilisation d'une expression régulière pour séparer par des caractères non alphabétiques ou numériques
    const words = input.split(/[^a-zA-Z0-9]+/).filter(word => word.trim() !== '');
    return words; 
  }
  updateUser(id:any,user:any,team:string,passwordAdmin:string,roles:string[]): Observable<any> {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const equipes=this.splitStringIntoWords(team);

      const updateRequest={
        email: user.email,
        password: user.password,
        nom: user.nom,
        prenom: user.prenom,
        societe:'' ,
        roles: roles,
        taches: [],
        equipes: equipes,
        accessToken: localStorage.getItem('loginToken'),
        passwordAdmin: passwordAdmin
    }
     

    return this.http.put<any>(this.apiurl+id, updateRequest, { headers });
  }

}
