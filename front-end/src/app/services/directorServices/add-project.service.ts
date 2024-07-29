import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AddProjectService {
  apiurl = 'http://localhost:9090/api/projets/add';

  constructor(private http:HttpClient) { }
  addProject(nom:string,description:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    let requestBody = {
      nomProjet: nom,
      description: description,
      taches:[]
    }
    return this.http.post<any>(this.apiurl,requestBody,{ headers });
  }
}
