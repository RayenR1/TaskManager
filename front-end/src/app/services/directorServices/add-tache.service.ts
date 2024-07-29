import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AddTacheService {

  apiurl = 'http://localhost:9090/api/taches/p-';

  constructor(private http:HttpClient) { }
  addTache(id:any,tache:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      let requestBody={   
      nomTache:tache.nomTache,
      type:tache.type,
      description:tache.description,
      dateDebut:tache.dateDebut,
      dateFin:tache.dateFin,
      etat:"Not_Affected",
      priorite:tache.priorite, 
    }
    return this.http.post<any>(this.apiurl+id+'/addTache',requestBody,{ headers });
  }
}
