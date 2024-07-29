import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UpdateEtatTacheService {

  apiurl = 'http://localhost:9090/api/employee/changeEtat/t-';

  constructor(private http:HttpClient) { }
  updateEtatTache(idt:any,etat:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>(this.apiurl+idt+'/newState-'+etat,{ headers });
  }
}
