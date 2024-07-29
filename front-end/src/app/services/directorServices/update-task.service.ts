import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UpdateTaskService {

  apiurl = 'http://localhost:9090/api/taches/p-';

  constructor(private http:HttpClient) { }
  updateTache(idp:any,idt:any,tache:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      let requestBody={   
      nomTache:tache.nomTache,
      type:tache.type,
      description:tache.description,
      dateDebut:tache.dateDebut,
      dateFin:tache.dateFin,
      etat:tache.etat,
      priorite:tache.priorite, 
    }
    return this.http.put<any>(this.apiurl+idp+'/updateTache-'+idt,requestBody,{ headers });
  }
}
